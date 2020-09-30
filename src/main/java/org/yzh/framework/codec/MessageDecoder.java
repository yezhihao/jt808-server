package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.FieldMetadata;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.model.RawMessage;
import org.yzh.framework.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import static org.yzh.framework.orm.model.DataType.*;

/**
 * 基础消息解码
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class MessageDecoder {

    private SessionManager sessionManager;

    public MessageDecoder(String basePackage) {
        MessageHelper.initial(basePackage);
    }

    public MessageDecoder(String basePackage, SessionManager sessionManager) {
        this(basePackage);
        this.sessionManager = sessionManager;
    }

    private static final Logger log = LoggerFactory.getLogger(MessageDecoder.class.getSimpleName());

    private MultiPacketManager multiPacketManager = MultiPacketManager.getInstance();

    /** 转码 */
    public abstract ByteBuf unescape(ByteBuf buf);

    /** 校验 */
    public abstract boolean verify(ByteBuf buf);

    public AbstractMessage decode(ByteBuf buf) {
        return decode(buf, 0);
    }

    public AbstractMessage decode(ByteBuf buf, int version) {
        buf = unescape(buf);

        boolean verified = verify(buf);
        if (!verified)
            log.error("校验码错误" + ByteBufUtil.hexDump(buf));
        buf = buf.slice(0, buf.readableBytes() - 1);

        Class<? extends AbstractHeader> headerClass = MessageHelper.getHeaderClass();
        BeanMetadata<? extends AbstractHeader> headMetadata = MessageHelper.getBeanMetadata(headerClass, version);
        int readerIndex = buf.readerIndex();

        AbstractHeader header = decode(buf, headMetadata);
        if (header.isVersion()) {
            buf.readerIndex(readerIndex);
            headMetadata = MessageHelper.getBeanMetadata(headerClass, 1);
            header = decode(buf, headMetadata);
            version = header.getVersionNo();
        }
        header.setVerified(verified);

        if (sessionManager != null) {
            Integer v = sessionManager.getVersion(header.getClientId());
            if (v != null) {
                version = v;
            }
        }

        Class<? extends AbstractMessage> bodyClass = MessageHelper.getBodyClass(header.getMessageId());
        if (bodyClass == null)
            bodyClass = RawMessage.class;

        BeanMetadata<? extends AbstractMessage> bodyMetadata = MessageHelper.getBeanMetadata(bodyClass, version);

        AbstractMessage message = null;
        int headLen = header.getHeadLength();
        int bodyLen = header.getBodyLength();

        if (header.isSubpackage()) {

            byte[] bytes = new byte[bodyLen];
            buf.readBytes(bytes);

            byte[][] packages = multiPacketManager.addAndGet(header, bytes);
            if (packages == null)
                return null;

            ByteBuf bodyBuf = Unpooled.wrappedBuffer(packages);
            message = decode(bodyBuf, bodyMetadata);

        } else {
            buf.readerIndex(headLen);
            message = decode(buf, bodyMetadata);
        }
        if (message == null)
            try {
                message = bodyClass.newInstance();
            } catch (Exception e) {
            }

        message.setHeader(header);
        return message;
    }


    public <T> T decode(ByteBuf buf, BeanMetadata<T> beanMetadata) {
        if (beanMetadata == null) {
            log.info("未找到 BeanMetadata");
            return null;
        }
        Class<? extends T> clazz = beanMetadata.typeClass;

        T result = null;
        boolean isEmpty = true;//防止死循环
        try {
            result = clazz.newInstance();
            for (FieldMetadata fieldMetadata : beanMetadata.fieldMetadataList) {
                Integer length = fieldMetadata.getLength(result);

                if (!buf.isReadable(length))
                    break;
                Object value = read(buf, fieldMetadata, length);
                fieldMetadata.writeMethod.invoke(result, value);
                isEmpty = false;
            }
        } catch (Exception e) {
            log.error("解码异常：" + clazz.getName(), e);
        }
        if (isEmpty)
            return null;
        return result;
    }

    public Object read(ByteBuf buf, FieldMetadata fieldMetadata, int length) {
        DataType type = fieldMetadata.dataType;
        if (DWORD == type) {
            if (fieldMetadata.isLong)
                return buf.readUnsignedInt();
            return (int) buf.readUnsignedInt();
        }
        if (WORD == type) {
            return buf.readUnsignedShort();
        }
        if (BYTE == type) {
            return (int) buf.readUnsignedByte();
        }

        if (length == -1)
            length = buf.readableBytes();

        if (OBJ == type) {
            return decode(buf.readSlice(length), fieldMetadata.beanMetadata);
        }
        if (LIST == type) {
            if (length <= 0)
                return null;
            List list = new ArrayList();
            ByteBuf slice = buf.readSlice(length);
            while (slice.isReadable()) {
                Object obj = decode(slice, fieldMetadata.beanMetadata);
                if (obj == null) break;
                list.add(obj);
            }
            return list;
        }

        if (STRING == type) {
            return buf.readCharSequence(length, fieldMetadata.charset).toString().trim();
        }

        if (BCD8421 == type) {
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            if (fieldMetadata.isDateTime)
                return Bcd.toDateTime(bytes);
            return Bcd.leftTrim(Bcd.toStr(bytes), '0');
        }
        if (fieldMetadata.isByteBuffer) {
            return buf.nioBuffer(buf.readerIndex(), length);
        }

        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        if (fieldMetadata.isString) {
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] != fieldMetadata.pad)
                    return new String(bytes, i, bytes.length - i, fieldMetadata.charset);
            }
            return new String(bytes, fieldMetadata.charset);
        }
        return bytes;
    }
}
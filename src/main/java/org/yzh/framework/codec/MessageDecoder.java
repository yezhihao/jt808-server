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

import java.util.ArrayList;
import java.util.List;

import static org.yzh.framework.orm.model.DataType.*;

/**
 * 基础消息解码
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class MessageDecoder {

    public MessageDecoder(String basePackage) {
        MessageHelper.initial(basePackage);
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
        int readerIndex = buf.readerIndex();

        AbstractHeader header = decode(buf, headerClass, version);
        header.setVerified(verified);

        if (header.isVersion()) {
            buf.readerIndex(readerIndex);
            header = decode(buf, headerClass, 1);
            version = header.getVersionNo();
        }

        Class<? extends AbstractMessage> bodyClass = MessageHelper.getBodyClass(header.getMessageId());
        if (bodyClass == null)
            bodyClass = RawMessage.class;

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
            message = decode(bodyBuf, bodyClass, version);

        } else {
            buf.readerIndex(headLen);
            message = decode(buf, bodyClass, version);
        }
        if (message == null)
            try {
                message = bodyClass.newInstance();
            } catch (Exception e) {
            }

        message.setHeader(header);
        return message;
    }


    public <T> T decode(ByteBuf buf, Class<T> clazz, int version) {
        BeanMetadata beanMetadata = MessageHelper.getBeanMetadata(clazz, version);
        if (beanMetadata == null) {
            log.warn(clazz.getName() + "未找到 BeanMetadata");
            return null;
        }

        T result = null;
        boolean isEmpty = true;//防止死循环
        try {
            result = clazz.newInstance();
            for (FieldMetadata fieldMetadata : beanMetadata.fieldMetadataList) {
                Integer length = fieldMetadata.getLength(result);

                if (!buf.isReadable(length))
                    break;
                Object value = read(buf, fieldMetadata, length, version);
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

    public Object read(ByteBuf buf, FieldMetadata fieldMetadata, int length, int version) {
        DataType type = fieldMetadata.dataType;
        if (type == DWORD) {
            if (fieldMetadata.isLong)
                return buf.readUnsignedInt();
            return (int) buf.readUnsignedInt();
        }
        if (type == WORD) {
            return buf.readUnsignedShort();
        }
        if (type == BYTE) {
            return (int) buf.readUnsignedByte();
        }

        if (length == -1)
            length = buf.readableBytes();

        if (type == OBJ) {
            return decode(buf.readSlice(length), fieldMetadata.classType, version);
        }
        if (type == LIST) {
            if (length <= 0)
                return null;
            List list = new ArrayList();
            ByteBuf slice = buf.readSlice(length);
            while (slice.isReadable()) {
                Object obj = decode(slice, fieldMetadata.actualType, version);
                if (obj == null) break;
                list.add(obj);
            }
            return list;
        }

        if (type == STRING) {
            return buf.readCharSequence(length, fieldMetadata.charset).toString().trim();
        }

        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        if (type == BCD8421)
            return Bcd.leftTrim(Bcd.bcdToStr(bytes), '0');

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
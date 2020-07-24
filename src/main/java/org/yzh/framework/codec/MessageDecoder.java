package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.FieldSpec;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.MessageSpec;
import org.yzh.framework.orm.PropertyUtils;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.RawMessage;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.yzh.framework.enums.DataType.*;

/**
 * 基础消息解码
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public abstract class MessageDecoder {

    public MessageDecoder(String basePackage) {
        MessageHelper.initial(basePackage);
    }

    private static final Logger log = LoggerFactory.getLogger(MessageDecoder.class.getSimpleName());

    /** 转码 */
    public abstract ByteBuf unescape(ByteBuf buf);

    /** 校验 */
    public abstract boolean verify(ByteBuf buf);

    public AbstractMessage decode(ByteBuf buf) {
        buf = unescape(buf);

        boolean verified = verify(buf);
        if (verified)
            log.error("校验码错误" + ByteBufUtil.hexDump(buf));

        Class<? extends AbstractHeader> headerClass = MessageHelper.getHeaderClass();
        int readerIndex = buf.readerIndex();
        int version = 0;

        AbstractHeader header = decode(buf, headerClass, version);
        header.setVerified(verified);

        if (header.isVersion()) {
            buf.readerIndex(readerIndex);
            header = decode(buf, headerClass, 1);
            version = header.getVersionNo();
        }

        Class<? extends AbstractMessage> bodyClass = MessageHelper.getBodyClass(header.getMessageId());
        AbstractMessage message;

        if (bodyClass == null) {
            message = new RawMessage();
            message.setHeader(header);
            return message;
        }

        int headLen = header.getHeadLength();
        int bodyLen = header.getBodyLength();

        if (header.isSubpackage()) {

            byte[] bytes = new byte[bodyLen];
            buf.readBytes(bytes);

            byte[][] packages = MultiPacketManager.Instance.addAndGet(header, bytes);
            if (packages != null) {

                ByteBuf bodyBuf = Unpooled.wrappedBuffer(packages);
                message = decode(bodyBuf, bodyClass, version);
                message.setHeader(header);
            } else {
                return null;
            }
        } else {
            buf.setIndex(headLen, headLen + bodyLen);
            message = decode(buf, bodyClass, version);
            message.setHeader(header);
        }
        return message;
    }


    public <T> T decode(ByteBuf buf, Class<T> clazz, int version) {
        T result = BeanUtils.newInstance(clazz);

        MessageSpec messageSpec = MessageHelper.getMessageSpec(clazz, version);
        if (messageSpec == null)
            throw new RuntimeException(clazz.getName() + "未找到 MessageSpec");

        for (FieldSpec fieldSpec : messageSpec.fieldSpecs) {

            int length = PropertyUtils.getLength(result, fieldSpec.field);
            if (!buf.isReadable(length))
                break;

            if (length == -1)
                length = buf.readableBytes();
            Object value = null;
            try {
                value = read(buf, fieldSpec, length, version);
            } catch (Exception e) {
                log.error("解码异常：", e);
            }
            BeanUtils.setValue(result, fieldSpec.writeMethod, value);
        }
        return result;
    }

    public Object read(ByteBuf buf, FieldSpec fieldSpec, int length, int version) {
        Field prop = fieldSpec.field;
        DataType type = prop.type();

        if (type == DWORD) {
            if (fieldSpec.type.isAssignableFrom(Long.class))
                return buf.readUnsignedInt();
            return (int) buf.readUnsignedInt();
        }
        if (type == WORD) {
            return buf.readUnsignedShort();
        }
        if (type == LIST) {
            List list = new ArrayList();
            Type clazz = ((ParameterizedType) fieldSpec.readMethod.getGenericReturnType()).getActualTypeArguments()[0];
            ByteBuf slice = buf.readSlice(length);
            while (slice.isReadable())
                list.add(decode(slice, (Class) clazz, version));
            return list;
        }
        if (type == BYTE) {
            return (int) buf.readUnsignedByte();
        }
        if (type == STRING) {
            return buf.readCharSequence(length, Charset.forName(prop.charset())).toString().trim();
        }

        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        if (type == BCD8421)
            return Bcd.leftTrim(Bcd.bcdToStr(bytes), '0');

        if (fieldSpec.type.isAssignableFrom(String.class)) {
            byte pad = prop.pad();
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] != pad)
                    return new String(bytes, i, bytes.length - i, Charset.forName(prop.charset()));
            }
            return new String(bytes, Charset.forName(prop.charset()));
        }
        if (type == OBJ) {
            return decode(buf.readSlice(length), fieldSpec.type, version);
        }
        return bytes;
    }
}
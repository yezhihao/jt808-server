package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.FieldSpec;
import org.yzh.framework.commons.MessageSpec;
import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 基础消息编码
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public abstract class MessageEncoder {

    private static final Logger log = LoggerFactory.getLogger(MessageEncoder.class.getSimpleName());

    /** 转码 */
    public abstract ByteBuf escape(ByteBuf buf);

    /** 签名 */
    public abstract ByteBuf sign(ByteBuf buf);

    public ByteBuf encode(AbstractMessage message) {
        AbstractHeader header = message.getHeader();
        int version = header.getVersionNo();

        ByteBuf bodyBuf = encode(message, version);

        int bodyLength = bodyBuf.readableBytes();
        if (bodyLength > 1023)
            throw new RuntimeException("消息体不能大于1023kb," + bodyLength + "Kb");
        header.setBodyLength(bodyLength);

        ByteBuf headerBuf = encode(header, version);
        ByteBuf allBuf = Unpooled.wrappedBuffer(headerBuf, bodyBuf);

        allBuf = sign(allBuf);
        allBuf = escape(allBuf);
        return allBuf;
    }

    private ByteBuf encode(Object obj, int version) {
        Class<?> clazz = obj.getClass();

        MessageSpec messageSpec = MessageHelper.getMessageSpec(clazz, version);
        if (messageSpec == null) {
            log.warn(clazz.getName() + "未找到 messageSpec");
            return Unpooled.EMPTY_BUFFER;
        }

        ByteBuf buf = Unpooled.buffer(messageSpec.length);
        for (FieldSpec fieldSpec : messageSpec.fieldSpecs) {

            Method readMethod = fieldSpec.readMethod;
            Object value = BeanUtils.getValue(obj, readMethod);
            if (value != null) {
                write(buf, fieldSpec, value, version);
            }
        }
        return buf;
    }

    private ByteBuf encode(List<Object> list, int version) {
        int size = list.size();
        if (size == 0)
            return Unpooled.EMPTY_BUFFER;
        Object first = list.get(0);
        Class<?> clazz = first.getClass();

        MessageSpec messageSpec = MessageHelper.getMessageSpec(clazz, version);
        if (messageSpec == null) {
            log.warn(clazz.getName() + "未找到 messageSpec");
            return Unpooled.EMPTY_BUFFER;
        }

        ByteBuf buf = Unpooled.buffer(messageSpec.length * size);
        for (Object obj : list) {
            for (FieldSpec fieldSpec : messageSpec.fieldSpecs) {

                Method readMethod = fieldSpec.readMethod;
                Object value = BeanUtils.getValue(obj, readMethod);
                if (value != null) {
                    write(buf, fieldSpec, value, version);
                }
            }
        }
        return buf;
    }

    public void write(ByteBuf buf, FieldSpec fieldSpec, Object value, int version) {
        Field field = fieldSpec.field;
        int length = field.length();
        byte pad = field.pad();

        switch (field.type()) {
            case BYTE:
                buf.writeByte((int) value);
                break;
            case WORD:
                buf.writeShort((int) value);
                break;
            case DWORD:
                if (value instanceof Long)
                    buf.writeInt(((Long) value).intValue());
                else
                    buf.writeInt((int) value);
                break;
            case BYTES:
                if (fieldSpec.type.isAssignableFrom(String.class)) {
                    byte[] bytes = ((String) value).getBytes(Charset.forName(field.charset()));
                    int srcLen = bytes.length;
                    if (length > 0) {
                        bytes = Bcd.checkRepair(bytes, length);
                        if (srcLen > bytes.length)
                            log.warn("数据长度超出限制[{}]原始长度{},目标长度{},[{}]", value, srcLen, bytes.length);
                    }
                    buf.writeBytes(bytes);
                } else {
                    if (length < 0) buf.writeBytes((byte[]) value);
                    else buf.writeBytes((byte[]) value, 0, length);
                }
                break;
            case BCD8421:
                String str = Bcd.leftPad((String) value, length * 2, '0');
                buf.writeBytes(Bcd.strToBcd(str));
                break;
            case STRING:
                byte[] bytes = ((String) value).getBytes(Charset.forName(field.charset()));
                int srcLen = bytes.length;
                if (length > 0) {
                    bytes = Bcd.checkRepair(bytes, length);
                    if (srcLen > bytes.length)
                        log.warn("数据长度超出限制,[{}]原始长度{},目标长度{},[{}]", value, srcLen, bytes.length);
                }
                buf.writeBytes(bytes);
                break;
            case OBJ:
                buf.writeBytes(encode(value, version));
                break;
            case LIST:
                if (value != null)
                    buf.writeBytes(encode((List) value, version));
                break;
        }
    }
}
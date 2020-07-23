package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.PropertySpec;
import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.annotation.Property;
import org.yzh.framework.orm.model.AbstractBody;
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
        ByteBuf buf;

        int version = message.getVersionNo();
        AbstractBody body = message.getBody();
        if (body != null) {

            ByteBuf bodyBuf = encode(Unpooled.buffer(256), body, version);

            int bodyLength = bodyBuf.readableBytes();
            if (bodyLength > 1023)
                throw new RuntimeException("消息体不能大于1023kb," + bodyLength + "Kb");
            message.setBodyLength(bodyLength);

            ByteBuf headerBuf = encode(Unpooled.buffer(16), message, version);
            buf = Unpooled.wrappedBuffer(headerBuf, bodyBuf);
        } else {
            buf = encode(Unpooled.buffer(16), message, version);
        }

        buf = sign(buf);
        buf = escape(buf);
        return buf;
    }


    private ByteBuf encode(ByteBuf buf, Object message, int version) {
        Class<?> clazz = message.getClass();

        PropertySpec[] propertySpecs = MessageHelper.getPropertySpec(clazz, version);
        if (propertySpecs == null)
            throw new RuntimeException(clazz.getName() + "未找到 PropertySpec");

        for (PropertySpec propertySpec : propertySpecs) {

            Method readMethod = propertySpec.readMethod;
            Object value = BeanUtils.getValue(message, readMethod);
            if (value != null) {
                write(buf, propertySpec, value, version);
            }
        }
        return buf;
    }

    public void write(ByteBuf buf, PropertySpec pd, Object value, int version) {
        Property prop = pd.property;
        int length = prop.length();
        byte pad = prop.pad();

        switch (prop.type()) {
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
                if (pd.type.isAssignableFrom(String.class)) {
                    byte[] bytes = ((String) value).getBytes(Charset.forName(prop.charset()));
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
                byte[] bytes = ((String) value).getBytes(Charset.forName(prop.charset()));
                int srcLen = bytes.length;
                if (length > 0) {
                    bytes = Bcd.checkRepair(bytes, length);
                    if (srcLen > bytes.length)
                        log.warn("数据长度超出限制,[{}]原始长度{},目标长度{},[{}]", value, srcLen, bytes.length);
                }
                buf.writeBytes(bytes);
                break;
            case OBJ:
                encode(buf, value, version);
                break;
            case LIST:
                List list = (List) value;
                for (Object obj : list)
                    encode(buf, obj, version);
                break;
        }
    }
}
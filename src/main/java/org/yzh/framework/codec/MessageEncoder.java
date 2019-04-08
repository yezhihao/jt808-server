package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.message.AbstractHeader;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 基础消息编码
 */
public abstract class MessageEncoder<T extends AbstractHeader> extends AbstractMessageCoder {

    public MessageEncoder(Charset charset) {
        super(charset);
    }

    /** 转义 */
    public abstract ByteBuf escape(ByteBuf buf);

    /** 签名 */
    public abstract ByteBuf sign(ByteBuf buf);

    public ByteBuf encode(PackageData<Header> body) {
        ByteBuf bodyBuf = encode(Unpooled.buffer(512), body);

        Header header = body.getHeader();
        header.setBodyLength(bodyBuf.readableBytes());

        ByteBuf headerBuf = encode(Unpooled.buffer(100), header);

        ByteBuf buf = Unpooled.wrappedBuffer(headerBuf, bodyBuf);

        buf = sign(buf);
        buf = escape(buf);

        return buf;
    }

    private ByteBuf encode(ByteBuf buf, Object body) {
        PropertyDescriptor[] pds = getPropertyDescriptor(body.getClass());

        for (PropertyDescriptor pd : pds) {

            Method readMethod = pd.getReadMethod();
            Object value = BeanUtils.getValue(body, readMethod);
            if (value != null) {
                Property prop = readMethod.getDeclaredAnnotation(Property.class);
                write(buf, prop, value);
            }
        }
        return buf;
    }

    public void write(ByteBuf buf, Property prop, Object value) {
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
                buf.writeBytes((byte[]) value);
                break;
            case BCD8421:
                buf.writeBytes(Bcd.leftPad(Bcd.decode8421((String) value), length, pad));
                break;
            case STRING:
                byte[] strBytes = ((String) value).getBytes(charset);
                if (length > 0)
                    strBytes = Bcd.leftPad(strBytes, length, pad);
                buf.writeBytes(strBytes);
                break;
            case OBJ:
                encode(buf, value);
                break;
            case LIST:
                List list = (List) value;
                for (Object o : list)
                    encode(buf, o);
                break;
        }
    }
}
package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.commons.transform.BCD8421Operator;
import org.yzh.framework.commons.transform.BitOperator;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractHeader;
import org.yzh.framework.message.PackageData;

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

    public abstract ByteBuf encodeAll(PackageData<T> body);

    public ByteBuf encode(Object body) {
        ByteBuf buf = Unpooled.buffer(512);
        encode(buf, body);
        return buf;
    }

    private ByteBuf encode(ByteBuf buf, Object body) {
        PropertyDescriptor[] pds = getPropertyDescriptor(body.getClass());

        for (PropertyDescriptor pd : pds) {

            Method readMethod = pd.getReadMethod();
            Object value = BeanUtils.getValue(body, readMethod);
            if (value != null) {
                Property prop = readMethod.getDeclaredAnnotation(Property.class);
                write(buf, prop.type(), prop.length(), prop.pad(), value);
            }
        }
        return buf;
    }

    public void write(ByteBuf buf, DataType type, int length, byte pad, Object value) {
        switch (type) {
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
                buf.writeBytes(BitOperator.leftPad(BCD8421Operator.string2Bcd((String) value), length, pad));
                break;
            case STRING:
                byte[] strBytes = ((String) value).getBytes(charset);
                if (length > 0)
                    strBytes = BitOperator.leftPad(strBytes, length, pad);
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
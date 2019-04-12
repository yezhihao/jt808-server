package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.commons.PropertyUtils;
import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.message.AbstractBody;
import org.yzh.framework.message.AbstractMessage;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 基础消息编码
 */
public abstract class MessageEncoder<T extends AbstractBody> extends MessageToByteEncoder<AbstractMessage<T>> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractMessage msg, ByteBuf out) {
        ByteBuf buf = encode(msg);
        out.writeByte(0x7e);
        out.writeBytes(buf);
        out.writeByte(0x7e);
    }

    public ByteBuf encode(AbstractMessage<T> message) {
        AbstractBody body = message.getBody();

        ByteBuf bodyBuf = encode(Unpooled.buffer(256), body);

        message.setBodyLength(bodyBuf.readableBytes());

        ByteBuf headerBuf = encode(Unpooled.buffer(16), message);

        ByteBuf buf = Unpooled.wrappedBuffer(headerBuf, bodyBuf);

        buf = sign(buf);
        buf = escape(buf);

        return buf;
    }

    /** 转义 */
    public abstract ByteBuf escape(ByteBuf buf);

    /** 签名 */
    public abstract ByteBuf sign(ByteBuf buf);

    private ByteBuf encode(ByteBuf buf, Object body) {
        PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptor(body.getClass());

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
                byte[] strBytes = ((String) value).getBytes(Charset.forName(prop.charset()));
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
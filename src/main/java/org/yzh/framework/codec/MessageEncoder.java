package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.commons.PropertySpec;
import org.yzh.framework.commons.PropertyUtils;
import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.message.AbstractBody;
import org.yzh.framework.message.AbstractMessage;

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
        int version = message.getVersionNo();

        ByteBuf bodyBuf = encode(Unpooled.buffer(256), body, version);

        message.setBodyLength(bodyBuf.readableBytes());

        ByteBuf headerBuf = encode(Unpooled.buffer(16), message, version);

        ByteBuf buf = Unpooled.wrappedBuffer(headerBuf, bodyBuf);

        buf = sign(buf);
        buf = escape(buf);

        return buf;
    }

    /** 转义 */
    public abstract ByteBuf escape(ByteBuf buf);

    /** 签名 */
    public abstract ByteBuf sign(ByteBuf buf);

    private ByteBuf encode(ByteBuf buf, Object body, int version) {
        PropertySpec[] pds = PropertyUtils.getPropertySpecs(body.getClass(), version);

        for (PropertySpec propertySpec : pds) {

            Method readMethod = propertySpec.readMethod;
            Object value = BeanUtils.getValue(body, readMethod);
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
                    byte[] strBytes = ((String) value).getBytes(Charset.forName(prop.charset()));
                    if (length > 0)
                        strBytes = Bcd.leftPad(strBytes, length, pad);
                    buf.writeBytes(strBytes);
                } else {
                    buf.writeBytes((byte[]) value);
                }
                break;
            case BCD8421:
                buf.writeBytes(Bcd.leftPad(Bcd.strToBcd((String) value), length, pad));
                break;
            case STRING:
                byte[] strBytes = ((String) value).getBytes(Charset.forName(prop.charset()));
                if (length > 0)
                    strBytes = Bcd.leftPad(strBytes, length, pad);
                buf.writeBytes(strBytes);
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
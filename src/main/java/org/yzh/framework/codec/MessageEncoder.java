package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(MessageEncoder.class.getSimpleName());

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

        int bodyLength = bodyBuf.readableBytes();
        if (bodyLength > 1023)
            throw new RuntimeException("消息体不能大于1023kb," + bodyLength + "Kb");
        message.setBodyLength(bodyLength);

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
        PropertySpec[] propertySpecs = PropertyUtils.getPropertySpecs(body.getClass(), version);

        if (propertySpecs != null)
            for (PropertySpec propertySpec : propertySpecs) {

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
package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.PropertySpec;
import org.yzh.framework.commons.PropertyUtils;
import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.MessageHelper;
import org.yzh.framework.orm.annotation.Property;
import org.yzh.framework.orm.model.AbstractBody;
import org.yzh.framework.orm.model.AbstractMessage;

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
 * @home http://gitee.com/yezhihao/jt808-server
 */
public abstract class MessageDecoder extends ByteToMessageDecoder {

    private static final Logger log = LoggerFactory.getLogger(MessageDecoder.class.getSimpleName());

    public MessageDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) {
        AbstractMessage message = decode(buf);
        out.add(message);
        buf.skipBytes(buf.readableBytes());
    }

    public AbstractMessage decode(ByteBuf buf) {

        buf = unEscape(buf);

        if (check(buf))
            System.out.println("校验码错误" + ByteBufUtil.hexDump(buf));

        int readerIndex = buf.readerIndex();
        int version = 0;
        Class<? extends AbstractMessage> headerClass = MessageHelper.getHeaderClass();

        AbstractMessage message = decode(buf, headerClass, version);

        if (message.isVersion()) {
            buf.readerIndex(readerIndex);
            message = decode(buf, headerClass, 1);
            version = message.getVersionNo();
        }

        Class<? extends AbstractBody> bodyClass = MessageHelper.getClass(message.getMessageId());

        if (bodyClass == null) {
            log.warn("未找到{}对应的BodyClass", Integer.toHexString(message.getMessageId()));
        } else {
            int headLen = message.getHeadLength();
            int bodyLen = message.getBodyLength();

            if (message.isSubpackage()) {

                byte[] bytes = new byte[bodyLen];
                buf.readBytes(bytes);

                byte[][] packages = MultiPacketManager.Instance.addAndGet(message, bytes);
                if (packages != null) {

                    ByteBuf bodyBuf = Unpooled.wrappedBuffer(packages);
                    AbstractBody body = decode(bodyBuf, bodyClass, version);
                    message.setBody(body);
                }
            } else {
                buf.setIndex(headLen, headLen + bodyLen);
                AbstractBody body = decode(buf, bodyClass, version);
                message.setBody(body);
            }
        }
        return message;
    }

    /** 反转义 */
    public abstract ByteBuf unEscape(ByteBuf buf);

    /** 校验 */
    public abstract boolean check(ByteBuf buf);

    public <T> T decode(ByteBuf buf, Class<T> targetClass, int version) {
        T result = BeanUtils.newInstance(targetClass);

        PropertySpec[] propertySpecs = MessageHelper.getPropertySpec(targetClass, version);
        if (propertySpecs == null)
            throw new RuntimeException(targetClass.getName() + "未找到 PropertySpec");
        for (PropertySpec propertySpec : propertySpecs) {

            int length = PropertyUtils.getLength(result, propertySpec.property);
            if (!buf.isReadable(length))
                break;

            if (length == -1)
                length = buf.readableBytes();
            Object value = null;
            try {
                value = read(buf, propertySpec, length, version);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BeanUtils.setValue(result, propertySpec.writeMethod, value);
        }
        return result;
    }

    public Object read(ByteBuf buf, PropertySpec propertySpec, int length, int version) {
        Property prop = propertySpec.property;
        DataType type = prop.type();

        if (type == DWORD) {
            if (propertySpec.type.isAssignableFrom(Long.class))
                return buf.readUnsignedInt();
            return (int) buf.readUnsignedInt();
        }
        if (type == WORD) {
            return buf.readUnsignedShort();
        }
        if (type == LIST) {
            List list = new ArrayList();
            Type clazz = ((ParameterizedType) propertySpec.readMethod.getGenericReturnType()).getActualTypeArguments()[0];
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

        if (propertySpec.type.isAssignableFrom(String.class)) {
            byte pad = prop.pad();
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] != pad)
                    return new String(bytes, i, bytes.length - i, Charset.forName(prop.charset()));
            }
            return new String(bytes, Charset.forName(prop.charset()));
        }
        if (type == OBJ) {
            return decode(buf.readSlice(length), propertySpec.type, version);
        }
        return bytes;
    }
}
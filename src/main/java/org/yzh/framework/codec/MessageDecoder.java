package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.commons.PropertySpec;
import org.yzh.framework.commons.PropertyUtils;
import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.mapping.Handler;
import org.yzh.framework.mapping.HandlerMapper;
import org.yzh.framework.message.AbstractBody;
import org.yzh.framework.message.AbstractMessage;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.yzh.framework.enums.DataType.*;

/**
 * 基础消息解码
 *
 * @author zhihao.ye (yezhihaoo@gmail.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
public abstract class MessageDecoder extends ByteToMessageDecoder {

    private HandlerMapper handlerMapper;

    public MessageDecoder() {
    }

    public MessageDecoder(HandlerMapper handlerMapper) {
        this.handlerMapper = handlerMapper;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int type = getType(in);
        Handler handler = handlerMapper.getHandler(type);

        if (handler == null) {
            return;
        }

        Type[] types = handler.getTargetParameterTypes();
        if (types[0] instanceof ParameterizedTypeImpl) {
            ParameterizedTypeImpl clazz = (ParameterizedTypeImpl) types[0];

            Class<? extends AbstractBody> bodyClass = (Class<? extends AbstractBody>) clazz.getActualTypeArguments()[0];
            Class<? extends AbstractMessage> messageClass = (Class<? extends AbstractMessage>) clazz.getRawType();
            AbstractMessage<? extends AbstractBody> decode = decode(in, messageClass, bodyClass);
            out.add(decode);
        } else {
            AbstractMessage<? extends AbstractBody> decode = decode(in, (Class) types[0], null);
            out.add(decode);
        }

        in.skipBytes(in.readableBytes());
    }

    /** 解析 */
    public <T extends AbstractBody> AbstractMessage<T> decode(ByteBuf buf, Class<? extends AbstractMessage> clazz, Class<T> bodyClass) {
        buf = unEscape(buf);

        if (check(buf))
            System.out.println("校验码错误" + ByteBufUtil.hexDump(buf));

        int readerIndex = buf.readerIndex();
        int version = 0;
        AbstractMessage message = decode(buf, clazz, version);
        if (message.isVersion()) {
            buf.readerIndex(readerIndex);
            message = decode(buf, clazz, 1);
            version = message.getVersionNo();
        }

        if (bodyClass != null) {
            Integer headerLength = message.getHeadLength();
            buf.setIndex(headerLength, headerLength + message.getBodyLength());
            T body = decode(buf, bodyClass, version);
            message.setBody(body);
        }
        return message;
    }

    /** 获取消息类型 */
    public abstract int getType(ByteBuf buf);

    /** 反转义 */
    public abstract ByteBuf unEscape(ByteBuf buf);

    /** 校验 */
    public abstract boolean check(ByteBuf buf);

    public <T> T decode(ByteBuf buf, Class<T> targetClass, int version) {
        T result = BeanUtils.newInstance(targetClass);

        PropertySpec[] propertySpecs = PropertyUtils.getPropertySpecs(targetClass, version);
        if (propertySpecs != null)
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
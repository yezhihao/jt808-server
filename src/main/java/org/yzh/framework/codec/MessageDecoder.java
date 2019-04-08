package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.commons.bean.BeanUtils;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractHeader;
import org.yzh.framework.message.PackageData;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
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
 */
public abstract class MessageDecoder extends AbstractMessageCoder {

    public MessageDecoder(Charset charset) {
        super(charset);
    }

    /** 获取消息类型 */
    public abstract int getType(ByteBuf buf);

    /** 反转义 */
    public abstract ByteBuf unEscape(ByteBuf buf);

    /** 校验 */
    public abstract boolean check(ByteBuf buf);

    /** 解析 */
    public <T extends PackageData> T decode(ByteBuf buf, Class<? extends AbstractHeader> headerClass, Class<T> bodyClass) {
        buf = unEscape(buf);

        if (check(buf))
            System.out.println("校验码错误" + ByteBufUtil.hexDump(buf));

        AbstractHeader header = decode(buf, headerClass);

        ByteBuf bodyBuf = buf.slice(header.getHeaderLength(), header.getBodyLength());

        T packageData = decode(bodyBuf, bodyClass);
        packageData.setHeader(header);
        return packageData;
    }

    public <T> T decode(ByteBuf buf, Class<T> targetClass) {
        T result = BeanUtils.newInstance(targetClass);

        PropertyDescriptor[] pds = getPropertyDescriptor(targetClass);
        for (PropertyDescriptor pd : pds) {

            Method readMethod = pd.getReadMethod();
            Property prop = readMethod.getDeclaredAnnotation(Property.class);
            int length = getLength(result, prop);
            if (!buf.isReadable(length))
                break;

            if (length == -1)
                length = buf.readableBytes();
            Object value = null;
            try {
                value = read(buf, prop, length, pd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BeanUtils.setValue(result, pd.getWriteMethod(), value);
        }
        return result;
    }

    public Object read(ByteBuf buf, Property prop, int length, PropertyDescriptor pd) {
        DataType type = prop.type();

        if (type == BYTE) {
            return (int) buf.readUnsignedByte();
        } else if (type == WORD) {
            return buf.readUnsignedShort();
        } else if (type == DWORD) {
            if (pd.getPropertyType().isAssignableFrom(Long.class))
                return buf.readUnsignedInt();
            return (int) buf.readUnsignedInt();
        } else if (type == STRING) {
            return buf.readCharSequence(length, charset).toString().trim();
        } else if (type == OBJ) {
            return decode(buf.readSlice(length), pd.getPropertyType());
        } else if (type == LIST) {
            List list = new ArrayList();
            Type clazz = ((ParameterizedType) pd.getReadMethod().getGenericReturnType()).getActualTypeArguments()[0];
            ByteBuf slice = buf.readSlice(length);
            while (slice.isReadable())
                list.add(decode(slice, (Class) clazz));
            return list;
        }

        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        if (type == BCD8421)
            return Bcd.encode8421String(bytes).trim();
        return bytes;
    }
}
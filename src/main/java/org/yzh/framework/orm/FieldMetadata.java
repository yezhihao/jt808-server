package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.fields.*;
import org.yzh.framework.orm.model.DataType;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;

/**
 * 消息定义
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class FieldMetadata<T> implements Comparable<FieldMetadata> {
    protected static Logger log = LoggerFactory.getLogger(FieldMetadata.class.getSimpleName());
    public static boolean EXPLAIN = false;

    protected final int index;
    protected final int length;
    protected final String desc;
    protected final Method readMethod;
    protected final Method writeMethod;
    protected final Method lengthMethod;
    protected final Field field;

    public FieldMetadata(Field field, Method readMethod, Method writeMethod, Method lengthMethod) {
        this.index = field.index();
        int length = field.length();
        if (length < 0)
            length = field.type().length;
        this.length = length;
        this.desc = field.desc();
        this.readMethod = readMethod;
        this.writeMethod = writeMethod;
        this.lengthMethod = lengthMethod;
        this.field = field;
    }

    public static FieldMetadata newInstance(Field field, Class typeClass, Method readMethod, Method writeMethod, Method lengthMethod) {
        return newInstance(field, typeClass, readMethod, writeMethod, lengthMethod, null);
    }

    public static FieldMetadata newInstance(Field field, Class typeClass, Method readMethod, Method writeMethod, Method lengthMethod, BeanMetadata beanMetadata) {
        DataType dataType = field.type();

        FieldMetadata result;
        switch (dataType) {
            case BYTE:
                result = new FieldInt8(field, readMethod, writeMethod, lengthMethod);
                break;
            case WORD:
                result = new FieldInt16(field, readMethod, writeMethod, lengthMethod);
                break;
            case DWORD:
                if (typeClass.isAssignableFrom(Long.class) || typeClass.isAssignableFrom(Long.TYPE))
                    result = new FieldLong32(field, readMethod, writeMethod, lengthMethod);
                else
                    result = new FieldInt32(field, readMethod, writeMethod, lengthMethod);
                break;
            case BCD8421:
                if (typeClass.isAssignableFrom(LocalDateTime.class))
                    result = new FieldDateTimeBCD(field, readMethod, writeMethod, lengthMethod);
                else
                    result = new FieldStringBCD(field, readMethod, writeMethod, lengthMethod);
                break;
            case BYTES:
                if (typeClass.isAssignableFrom(String.class))
                    result = new FieldString(field, readMethod, writeMethod, lengthMethod);
                else if (typeClass.isAssignableFrom(ByteBuffer.class))
                    result = new FieldByteBuffer(field, readMethod, writeMethod, lengthMethod);
                else
                    result = new FieldBytes(field, readMethod, writeMethod, lengthMethod);
                break;
            case STRING:
                result = new FieldString(field, readMethod, writeMethod, lengthMethod);
                break;
            case OBJ:
                result = new FieldObject(field, readMethod, writeMethod, lengthMethod, beanMetadata);
                break;
            case LIST:
                result = new FieldList(field, readMethod, writeMethod, lengthMethod, beanMetadata);
                break;
            default:
                throw new RuntimeException("不支持的类型转换");
        }
        if (EXPLAIN)
            return new LoggerProxy(result);
        return result;
    }

    public abstract T readValue(ByteBuf buf, int length);

    public abstract void writeValue(ByteBuf buf, T value);

    public final Object getValue(Object object) throws Exception {
        return readMethod.invoke(object);
    }

    public final void setValue(Object object, T value) throws Exception {
        writeMethod.invoke(object, value);
    }

    public Integer getLength(Object obj) throws Exception {
        if (lengthMethod == null)
            return length;
        return (Integer) lengthMethod.invoke(obj);
    }

    @Override
    public int compareTo(FieldMetadata that) {
        int r = Integer.compare(this.index, that.index);
        if (r == 0) {
            if (this.lengthMethod == null)
                r = 1;
            else if (that.lengthMethod == null)
                r = -1;
        }
        return r;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(80);
        sb.append('{');
        sb.append("index=").append(index);
        sb.append(", length=").append(length);
        sb.append(", desc").append(desc);
        sb.append(", readMethod=").append(readMethod.getName());
        sb.append(", writeMethod=").append(writeMethod.getName());
        if (lengthMethod != null)
            sb.append(", lengthMethod=").append(lengthMethod.getName());
        sb.append('}');
        return sb.toString();
    }
}
package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.fields.*;
import org.yzh.framework.orm.model.DataType;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
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

    public FieldMetadata(int index, int length, String desc, Method readMethod, Method writeMethod, Method lengthMethod) {
        this.index = index;
        this.length = length;
        this.desc = desc;
        this.readMethod = readMethod;
        this.writeMethod = writeMethod;
        this.lengthMethod = lengthMethod;
    }

    public static FieldMetadata newInstance(Class typeClass, Method readMethod, Method writeMethod, Method lengthMethod, Field field) {
        return newInstance(typeClass, readMethod, writeMethod, lengthMethod, field, null);
    }

    public static FieldMetadata newInstance(Class typeClass, Method readMethod, Method writeMethod, Method lengthMethod, Field field, BeanMetadata beanMetadata) {
        DataType dataType = field.type();
        String desc = field.desc();
        int index = field.index();
        int length = field.length();
        if (length < 0)
            length = field.type().length;

        FieldMetadata result;
        switch (dataType) {
            case BYTE:
                result = new FieldInt8(index, length, desc, readMethod, writeMethod, lengthMethod);
                break;
            case WORD:
                result = new FieldInt16(index, length, desc, readMethod, writeMethod, lengthMethod);
                break;
            case DWORD:
                if (typeClass.isAssignableFrom(Long.class) || typeClass.isAssignableFrom(Long.TYPE))
                    result = new FieldLong32(index, length, desc, readMethod, writeMethod, lengthMethod);
                else
                    result = new FieldInt32(index, length, desc, readMethod, writeMethod, lengthMethod);
                break;
            case BCD8421:
                if (typeClass.isAssignableFrom(LocalDateTime.class))
                    result = new FieldDateTimeBCD(index, length, desc, readMethod, writeMethod, lengthMethod);
                else
                    result = new FieldStringBCD(index, length, desc, readMethod, writeMethod, lengthMethod);
                break;
            case BYTES:
                if (typeClass.isAssignableFrom(String.class))
                    result = new FieldString(index, length, desc, readMethod, writeMethod, lengthMethod, field.pad(), Charset.forName(field.charset()));
                else if (typeClass.isAssignableFrom(ByteBuffer.class))
                    result = new FieldByteBuffer(index, length, desc, readMethod, writeMethod, lengthMethod);
                else
                    result = new FieldBytes(index, length, desc, readMethod, writeMethod, lengthMethod);
                break;
            case STRING:
                result = new FieldString(index, length, desc, readMethod, writeMethod, lengthMethod, field.pad(), Charset.forName(field.charset()));
                break;
            case OBJ:
                result = new FieldObject(index, length, desc, readMethod, writeMethod, lengthMethod, beanMetadata);
                break;
            case LIST:
                result = new FieldList(index, length, desc, readMethod, writeMethod, lengthMethod, beanMetadata);
                break;
            default:
                throw new RuntimeException("不支持的类型转换");
        }
        if (EXPLAIN)
            return new LoggerProxy(result);
        return result;
    }

    public abstract T readValue(ByteBuf buf, int length);

    public abstract void writeValue(ByteBuf buf, Object value);

    public final Object getValue(Object object) throws Exception {
        return readMethod.invoke(object);
    }

    public final void setValue(Object object, Object value) throws Exception {
        writeMethod.invoke(object, value);
    }

    public Integer getLength(Object obj) throws Exception {
        if (lengthMethod == null)
            return length;
        return (Integer) lengthMethod.invoke(obj);
    }

    @Override
    public String toString() {
        return "{" +
                "desc='" + desc + '\'' +
                ", readMethod=" + readMethod +
                ", writeMethod=" + writeMethod +
                ", length=" + length +
                ", lengthMethod=" + lengthMethod +
                '}';
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
}
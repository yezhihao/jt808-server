package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.fields.*;
import org.yzh.framework.orm.model.DataType;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;

/**
 * 消息定义
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class BasicField<T> implements Comparable<BasicField> {
    protected static Logger log = LoggerFactory.getLogger(BasicField.class.getSimpleName());
    public static boolean EXPLAIN = false;

    protected final int index;
    protected final int length;
    protected final String desc;
    protected final Method readMethod;
    protected final Method writeMethod;
    protected final PropertyDescriptor property;
    protected final Field field;

    public BasicField(Field field, PropertyDescriptor property) {
        this.index = field.index();
        int length = field.length();
        if (length < 0)
            length = field.type().length;
        this.length = length;
        this.desc = field.desc();
        this.readMethod = property.getReadMethod();
        this.writeMethod = property.getWriteMethod();
        this.field = field;
        this.property = property;
    }

    public static BasicField newInstance(Field field, Class typeClass, PropertyDescriptor property, PropertyDescriptor lengthProperty) {
        return newInstance(field, typeClass, property, lengthProperty, null);
    }

    public static BasicField newInstance(Field field, Class typeClass, PropertyDescriptor property, PropertyDescriptor lengthProperty, BeanMetadata beanMetadata) {
        DataType dataType = field.type();

        BasicField result;
        if (lengthProperty == null) {
            switch (dataType) {
                case BYTE:
                    result = new FieldInt8(field, property);
                    break;
                case WORD:
                    result = new FieldInt16(field, property);
                    break;
                case DWORD:
                    if (typeClass.isAssignableFrom(Long.class) || typeClass.isAssignableFrom(Long.TYPE))
                        result = new FieldLong32(field, property);
                    else
                        result = new FieldInt32(field, property);
                    break;
                case BCD8421:
                    if (typeClass.isAssignableFrom(LocalDateTime.class))
                        result = new FieldDateTimeBCD(field, property);
                    else
                        result = new FieldStringBCD(field, property);
                    break;
                case BYTES:
                    if (typeClass.isAssignableFrom(String.class))
                        result = new FieldString(field, property);
                    else if (typeClass.isAssignableFrom(ByteBuffer.class))
                        result = new FieldByteBuffer(field, property);
                    else
                        result = new FieldBytes(field, property);
                    break;
                case STRING:
                    result = new FieldString(field, property);
                    break;
                case OBJ:
                    result = new FieldObject(field, property, beanMetadata);
                    break;
                case LIST:
                    result = new FieldList(field, property, beanMetadata);
                    break;
                default:
                    throw new RuntimeException("不支持的类型转换");
            }
        } else {
            switch (dataType) {
                case BYTES:
                    if (typeClass.isAssignableFrom(String.class))
                        result = new DynamicFieldString(field, property, lengthProperty);
                    else if (typeClass.isAssignableFrom(ByteBuffer.class))
                        result = new DynamicFieldByteBuffer(field, property, lengthProperty);
                    else
                        result = new DynamicFieldBytes(field, property, lengthProperty);
                    break;
                case STRING:
                    result = new DynamicFieldString(field, property, lengthProperty);
                    break;
                case OBJ:
                    result = new DynamicFieldObject(field, property, lengthProperty, beanMetadata);
                    break;
                default:
                    throw new RuntimeException("不支持的类型转换");
            }
        }
        if (EXPLAIN)
            return new LoggerProxy(result);
        return result;
    }

    public abstract T readValue(ByteBuf buf, int length);

    public abstract void writeValue(ByteBuf buf, T value);

    public boolean readTo(ByteBuf buf, Object target) throws Exception {
        if (!buf.isReadable(length))
            return false;
        Object value = readValue(buf, length);
        writeMethod.invoke(target, value);
        return true;
    }

    public void writeTo(Object source, ByteBuf buf) throws Exception {
        Object value = readMethod.invoke(source);
        if (value != null)
            writeValue(buf, (T) value);
    }

    @Override
    public int compareTo(BasicField that) {
        return Integer.compare(this.index, that.index);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(60);
        sb.append('{');
        sb.append("index=").append(index);
        sb.append(", length=").append(length);
        sb.append(", desc").append(desc);
        sb.append(", readMethod=").append(readMethod.getName());
        sb.append(", writeMethod=").append(writeMethod.getName());
        sb.append('}');
        return sb.toString();
    }
}
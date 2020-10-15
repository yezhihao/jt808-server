package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public abstract class DynamicField<T> extends BasicField<T> {

    protected final Method lengthReadMethod;
    protected final Method lengthWriteMethod;
    protected final PropertyDescriptor lengthProperty;
    protected final int lengthFieldLength;

    public DynamicField(Field field, PropertyDescriptor property, PropertyDescriptor lengthProperty) {
        super(field, property);
        this.lengthReadMethod = lengthProperty.getReadMethod();
        this.lengthWriteMethod = lengthProperty.getWriteMethod();
        this.lengthProperty = lengthProperty;
        int length = field.lengthSize();
        Field lengthField = lengthReadMethod.getAnnotation(Field.class);
        if (lengthField != null)
            length = lengthField.type().length;
        this.lengthFieldLength = length;
    }

    public boolean readTo(ByteBuf buf, Object target) throws Exception {
        int length = getLength(target);
        if (!buf.isReadable(length))
            return false;
        Object value = readValue(buf, length);
        writeMethod.invoke(target, value);
        return true;
    }

    public void writeTo(Object source, ByteBuf buf) throws Exception {
        Object value = readMethod.invoke(source);
        if (value != null) {
            int begin = buf.writerIndex();
            writeValue(buf, (T) value);
            int length = buf.writerIndex() - begin;
            setLength(buf, begin - lengthFieldLength, length);
        }
    }

    public Integer getLength(Object obj) throws Exception {
        return (Integer) lengthReadMethod.invoke(obj);
    }

    public void setLength(Object obj, Object value) throws Exception {
        lengthWriteMethod.invoke(obj, value);
    }

    protected void setLength(ByteBuf buf, int offset, int length) {
        switch (lengthFieldLength) {
            case 1:
                buf.setByte(offset, length);
                break;
            case 2:
                buf.setShort(offset, length);
                break;
            case 3:
                buf.setMedium(offset, length);
                break;
            case 4:
                buf.setInt(offset, length);
                break;
            default:
                throw new RuntimeException("unsupported lengthFieldLength: " + lengthFieldLength + " (expected: 1, 2, 3, 4)");
        }
    }

    @Override
    public int compareTo(BasicField that) {
        int r = Integer.compare(this.index, that.index);
        if (r == 0) {
            if (BasicField.class.isAssignableFrom(that.getClass()))
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
        if (lengthReadMethod != null)
            sb.append(", lengthReadMethod=").append(lengthReadMethod.getName());
        sb.append('}');
        return sb.toString();
    }
}
package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public abstract class DynamicField<T> extends BasicField<T> {

    private final Method lengthReadMethod;
    private final Method lengthWriteMethod;

    public DynamicField(Field field, PropertyDescriptor property, PropertyDescriptor lengthProperty) {
        super(field, property);
        if (lengthProperty != null) {
            this.lengthReadMethod = lengthProperty.getReadMethod();
            this.lengthWriteMethod = lengthProperty.getWriteMethod();
        } else {
            this.lengthReadMethod = null;
            this.lengthWriteMethod = null;
        }
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
            int start = buf.writerIndex();
            writeValue(buf, (T) value);
            setLength(source, buf.writerIndex() - start);
        }
    }

    public Integer getLength(Object obj) throws Exception {
        if (lengthReadMethod == null)
            return length;
        return (Integer) lengthReadMethod.invoke(obj);
    }

    public void setLength(Object obj, Object value) throws Exception {
        if (lengthWriteMethod != null)
            lengthWriteMethod.invoke(obj, value);
    }

    @Override
    public int compareTo(BasicField that) {
        int r = Integer.compare(this.index, that.index);
        if (r == 0) {
            if (this.lengthReadMethod == null)
                r = 1;
            else
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
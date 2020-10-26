package org.yzh.framework.orm.field;

import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 固定长度的字段
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class BasicField<T> implements Comparable<BasicField<T>> {
    protected static Logger log = LoggerFactory.getLogger(BasicField.class.getSimpleName());

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

    public abstract boolean readFrom(ByteBuf input, Object message) throws Exception;

    public abstract void writeTo(ByteBuf output, Object message) throws Exception;

    public void println(int index, String desc, String hex, Object value) {
        if (value != null)
            System.out.println(index + "\t" + "[" + hex + "] " + desc + ": " + (value.getClass().isArray() ? ArrayUtils.toString(value) : value));
    }

    public int index() {
        return index;
    }

    public int length() {
        return length;
    }

    @Override
    public int compareTo(BasicField<T> that) {
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
package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.FieldMetadata;

import java.lang.reflect.Method;

public class FieldObject<T> extends FieldMetadata<T> {

    protected BeanMetadata<T> beanMetadata;

    public FieldObject(int index, int length, String desc, Method readMethod, Method writeMethod, Method lengthMethod, BeanMetadata<T> beanMetadata) {
        super(index, length, desc, readMethod, writeMethod, lengthMethod);
        this.beanMetadata = beanMetadata;
    }


    @Override
    public T readValue(ByteBuf buf, int length) {
        if (length > 0)
            buf = buf.readSlice(length);
        return beanMetadata.decode(buf);
    }

    @Override
    public void writeValue(ByteBuf buf, T obj) {
        beanMetadata.encode(buf, obj);
    }
}
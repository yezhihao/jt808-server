package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.DynamicField;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

public class DynamicFieldObject<T> extends DynamicField<T> {

    protected BeanMetadata<T> beanMetadata;

    public DynamicFieldObject(Field field, PropertyDescriptor property, PropertyDescriptor lengthProperty, BeanMetadata<T> beanMetadata) {
        super(field, property, lengthProperty);
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
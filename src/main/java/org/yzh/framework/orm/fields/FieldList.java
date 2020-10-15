package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.BasicField;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class FieldList<T> extends BasicField<List<T>> {

    protected final BeanMetadata<T> beanMetadata;

    public FieldList(Field field, PropertyDescriptor property, BeanMetadata<T> beanMetadata) {
        super(field, property);
        this.beanMetadata = beanMetadata;
    }

    @Override
    public List<T> readValue(ByteBuf buf, int length) {
        if (!buf.isReadable())
            return null;
        List<T> list = new ArrayList<>();
        do {
            T obj = beanMetadata.decode(buf);
            if (obj == null) break;
            list.add(obj);
        } while (buf.isReadable());
        return list;
    }

    @Override
    public void writeValue(ByteBuf buf, List<T> list) {
        if (list == null || list.isEmpty())
            return;

        for (Object obj : list) {
            beanMetadata.encode(buf, obj);
        }
    }
}
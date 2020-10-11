package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.BeanMetadata;
import org.yzh.framework.orm.FieldMetadata;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FieldList<T> extends FieldMetadata<List<T>> {

    protected final BeanMetadata<T> beanMetadata;

    public FieldList(int index, int length, String desc, Method readMethod, Method writeMethod, Method lengthMethod, BeanMetadata<T> beanMetadata) {
        super(index, length, desc, readMethod, writeMethod, lengthMethod);
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
    public void writeValue(ByteBuf buf, Object value) {
        List list = (List) value;
        if (list == null || list.isEmpty())
            return;

        for (Object obj : list) {
            beanMetadata.encode(buf, obj);
        }
    }
}
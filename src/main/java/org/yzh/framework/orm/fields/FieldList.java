package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.BasicField;
import org.yzh.framework.orm.Schema;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class FieldList<T> extends BasicField<List<T>> {

    protected final Schema<T> schema;

    public FieldList(Field field, PropertyDescriptor property, Schema<T> schema) {
        super(field, property);
        this.schema = schema;
    }

    @Override
    public List<T> readValue(ByteBuf input, int length) {
        if (!input.isReadable())
            return null;
        List<T> list = new ArrayList<>();
        do {
            T obj = schema.readFrom(input);
            if (obj == null) break;
            list.add(obj);
        } while (input.isReadable());
        return list;
    }

    @Override
    public void writeValue(ByteBuf output, List<T> list) {
        if (list == null || list.isEmpty())
            return;

        for (T obj : list) {
            schema.writeTo(output, obj);
        }
    }
}
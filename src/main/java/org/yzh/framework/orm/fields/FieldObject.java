package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.BasicField;
import org.yzh.framework.orm.Schema;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

public class FieldObject<T> extends BasicField<T> {

    protected Schema<T> schema;

    public FieldObject(Field field, PropertyDescriptor property, Schema<T> schema) {
        super(field, property);
        this.schema = schema;
    }

    @Override
    public T readValue(ByteBuf input, int length) {
        if (length > 0)
            input = input.readSlice(length);
        return schema.readFrom(input);
    }

    @Override
    public void writeValue(ByteBuf output, T obj) {
        schema.writeTo(output, obj);
    }
}
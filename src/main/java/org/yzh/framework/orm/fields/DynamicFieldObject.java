package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.DynamicField;
import org.yzh.framework.orm.Schema;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

public class DynamicFieldObject<T> extends DynamicField<T> {

    protected Schema<T> schema;

    public DynamicFieldObject(Field field, PropertyDescriptor property, Schema<T> schema) {
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
    public void writeValue(ByteBuf output, T value) {
        schema.writeTo(output, value);
    }
}
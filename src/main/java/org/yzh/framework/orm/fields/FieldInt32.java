package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

public class FieldInt32 extends BasicField<Integer> {

    public FieldInt32(Field field, PropertyDescriptor property) {
        super(field, property);
    }

    @Override
    public Integer readValue(ByteBuf input, int length) {
        return (int) input.readUnsignedInt();
    }

    @Override
    public void writeValue(ByteBuf output, Integer value) {
        output.writeInt(value);
    }
}
package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.BasicField;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

public class FieldInt8 extends BasicField<Integer> {

    public FieldInt8(Field field, PropertyDescriptor property) {
        super(field, property);
    }

    @Override
    public Integer readValue(ByteBuf buf, int length) {
        return (int) buf.readUnsignedByte();
    }

    @Override
    public void writeValue(ByteBuf buf, Integer value) {
        buf.writeByte(value);
    }
}
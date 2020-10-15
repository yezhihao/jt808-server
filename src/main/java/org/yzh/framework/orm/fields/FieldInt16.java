package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.BasicField;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

public class FieldInt16 extends BasicField<Integer> {

    public FieldInt16(Field field, PropertyDescriptor property) {
        super(field, property);
    }

    @Override
    public Integer readValue(ByteBuf buf, int length) {
        return buf.readUnsignedShort();
    }

    @Override
    public void writeValue(ByteBuf buf, Integer value) {
        buf.writeShort(value);
    }
}
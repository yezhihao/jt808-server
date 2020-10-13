package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.FieldMetadata;

import java.lang.reflect.Method;

public class FieldInt16 extends FieldMetadata<Integer> {

    public FieldInt16(int index, int length, String desc, Method readMethod, Method writeMethod, Method lengthMethod) {
        super(index, length, desc, readMethod, writeMethod, lengthMethod);
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
package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.FieldMetadata;
import org.yzh.framework.orm.annotation.Field;

import java.lang.reflect.Method;

public class FieldInt32 extends FieldMetadata<Integer> {

    public FieldInt32(Field field, Method readMethod, Method writeMethod, Method lengthMethod) {
        super(field, readMethod, writeMethod, lengthMethod);
    }

    @Override
    public Integer readValue(ByteBuf buf, int length) {
        return (int) buf.readUnsignedInt();
    }

    @Override
    public void writeValue(ByteBuf buf, Integer value) {
        buf.writeInt(value);
    }
}
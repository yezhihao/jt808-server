package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.FieldMetadata;

import java.lang.reflect.Method;

public class FieldLong32 extends FieldMetadata<Long> {

    public FieldLong32(int index, int length, String desc, Method readMethod, Method writeMethod, Method lengthMethod) {
        super(index, length, desc, readMethod, writeMethod, lengthMethod);
    }

    @Override
    public Long readValue(ByteBuf buf, int length) {
        return buf.readUnsignedInt();
    }

    @Override
    public void writeValue(ByteBuf buf, Long value) {
        buf.writeInt(value.intValue());
    }
}
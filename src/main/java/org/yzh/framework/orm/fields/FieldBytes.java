package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.FieldMetadata;

import java.lang.reflect.Method;

public class FieldBytes extends FieldMetadata<byte[]> {

    public FieldBytes(int index, int length, String desc, Method readMethod, Method writeMethod, Method lengthMethod) {
        super(index, length, desc, readMethod, writeMethod, lengthMethod);
    }

    @Override
    public byte[] readValue(ByteBuf buf, int length) {
        if (length < 0)
            length = buf.readableBytes();
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        return bytes;
    }

    @Override
    public void writeValue(ByteBuf buf, byte[] value) {
        if (length < 0)
            buf.writeBytes(value);
        else
            buf.writeBytes(value, 0, length);
    }
}
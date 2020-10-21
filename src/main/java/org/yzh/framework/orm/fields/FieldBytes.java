package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;

public class FieldBytes extends BasicField<byte[]> {

    public FieldBytes(Field field, PropertyDescriptor property) {
        super(field, property);
    }

    @Override
    public byte[] readValue(ByteBuf input, int length) {
        if (length < 0)
            length = input.readableBytes();
        byte[] bytes = new byte[length];
        input.readBytes(bytes);
        return bytes;
    }

    @Override
    public void writeValue(ByteBuf output, byte[] value) {
        if (length < 0)
            output.writeBytes(value);
        else
            output.writeBytes(value, 0, length);
    }
}
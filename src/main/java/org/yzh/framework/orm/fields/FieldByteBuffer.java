package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.BasicField;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;
import java.nio.ByteBuffer;

public class FieldByteBuffer extends BasicField<ByteBuffer> {

    public FieldByteBuffer(Field field, PropertyDescriptor property) {
        super(field, property);
    }

    @Override
    public ByteBuffer readValue(ByteBuf input, int length) {
        if (length < 0)
            length = input.readableBytes();
        ByteBuffer byteBuffer = input.nioBuffer(input.readerIndex(), length);
        input.skipBytes(length);
        return byteBuffer;
    }

    @Override
    public void writeValue(ByteBuf output, ByteBuffer value) {
        if (length > 0)
            value.position(value.limit() - length);
        output.writeBytes(value);
    }
}
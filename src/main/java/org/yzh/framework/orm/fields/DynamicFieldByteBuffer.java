package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;
import java.nio.ByteBuffer;

public class DynamicFieldByteBuffer extends DynamicField<ByteBuffer> {

    public DynamicFieldByteBuffer(Field field, PropertyDescriptor property) {
        super(field, property);
    }

    @Override
    public ByteBuffer readValue(ByteBuf input, int length) {
        ByteBuffer byteBuffer = input.nioBuffer(input.readerIndex(), length);
        input.skipBytes(length);
        return byteBuffer;
    }

    @Override
    public void writeValue(ByteBuf output, ByteBuffer byteBuffer) {
        if (length > 0)
            byteBuffer.position(byteBuffer.limit() - length);
        output.writeBytes(byteBuffer);
    }
}
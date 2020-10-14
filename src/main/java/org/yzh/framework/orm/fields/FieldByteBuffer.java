package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.FieldMetadata;
import org.yzh.framework.orm.annotation.Field;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class FieldByteBuffer extends FieldMetadata<ByteBuffer> {

    public FieldByteBuffer(Field field, Method readMethod, Method writeMethod, Method lengthMethod) {
        super(field, readMethod, writeMethod, lengthMethod);
    }

    @Override
    public ByteBuffer readValue(ByteBuf buf, int length) {
        ByteBuffer byteBuffer = buf.nioBuffer(buf.readerIndex(), length);
        buf.skipBytes(length);
        return byteBuffer;
    }

    @Override
    public void writeValue(ByteBuf buf, ByteBuffer byteBuffer) {
        if (length > 0)
            byteBuffer.position(byteBuffer.limit() - length);
        buf.writeBytes(byteBuffer);
    }
}
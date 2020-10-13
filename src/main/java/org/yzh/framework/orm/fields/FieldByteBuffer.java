package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.FieldMetadata;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class FieldByteBuffer extends FieldMetadata {

    public FieldByteBuffer(int index, int length, String desc, Method readMethod, Method writeMethod, Method lengthMethod) {
        super(index, length, desc, readMethod, writeMethod, lengthMethod);
    }

    @Override
    public Object readValue(ByteBuf buf, int length) {
        ByteBuffer byteBuffer = buf.nioBuffer(buf.readerIndex(), length);
        buf.skipBytes(length);
        return byteBuffer;
    }

    @Override
    public void writeValue(ByteBuf buf, Object value) {
        ByteBuffer byteBuffer = (ByteBuffer) value;
        if (length > 0)
            byteBuffer.position(byteBuffer.limit() - length);
        buf.writeBytes(byteBuffer);
    }
}
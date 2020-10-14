package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.DynamicField;
import org.yzh.framework.orm.annotation.Field;

import java.beans.PropertyDescriptor;
import java.nio.ByteBuffer;

public class DynamicFieldByteBuffer extends DynamicField<ByteBuffer> {

    public DynamicFieldByteBuffer(Field field, PropertyDescriptor property, PropertyDescriptor lengthProperty) {
        super(field, property, lengthProperty);
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
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
    public ByteBuffer readValue(ByteBuf buf, int length) {
        if (length < 0)
            length = buf.readableBytes();
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
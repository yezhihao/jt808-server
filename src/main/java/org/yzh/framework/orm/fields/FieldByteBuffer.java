package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;

import java.nio.ByteBuffer;

public class FieldByteBuffer implements Schema<ByteBuffer> {

    public static final Schema INSTANCE = new FieldByteBuffer();

    private FieldByteBuffer() {
    }

    @Override
    public ByteBuffer readFrom(ByteBuf input) {
        ByteBuffer message = input.nioBuffer();
        input.skipBytes(input.readableBytes());
        return message;
    }

    @Override
    public ByteBuffer readFrom(ByteBuf input, int length) {
        if (length < 0)
            length = input.readableBytes();
        ByteBuffer byteBuffer = input.nioBuffer(input.readerIndex(), length);
        input.skipBytes(length);
        return byteBuffer;
    }

    @Override
    public void writeTo(ByteBuf output, ByteBuffer value) {
        output.writeBytes(value);
    }

    @Override
    public void writeTo(ByteBuf output, int length, ByteBuffer value) {
        if (length > 0)
            value.position(value.limit() - length);
        output.writeBytes(value);
    }
}
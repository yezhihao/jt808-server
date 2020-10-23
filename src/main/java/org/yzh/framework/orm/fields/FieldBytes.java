package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;

public class FieldBytes implements Schema<byte[]> {

    public static final Schema INSTANCE = new FieldBytes();

    private FieldBytes() {
    }

    @Override
    public byte[] readFrom(ByteBuf input) {
        byte[] message = new byte[input.readableBytes()];
        input.readBytes(message);
        return message;
    }

    @Override
    public byte[] readFrom(ByteBuf input, int length) {
        if (length < 0)
            length = input.readableBytes();
        byte[] bytes = new byte[length];
        input.readBytes(bytes);
        return bytes;
    }

    @Override
    public void writeTo(ByteBuf output, byte[] value) {
        output.writeBytes(value);
    }

    @Override
    public void writeTo(ByteBuf output, int length, byte[] message) {
        output.writeBytes(message, 0, length);
    }
}
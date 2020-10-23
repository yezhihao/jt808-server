package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;

public class FieldInt8 implements Schema<Integer> {

    public static final Schema INSTANCE = new FieldInt8();

    private FieldInt8() {
    }

    @Override
    public Integer readFrom(ByteBuf input) {
        return (int) input.readUnsignedByte();
    }

    @Override
    public void writeTo(ByteBuf output, Integer value) {
        output.writeByte(value);
    }
}
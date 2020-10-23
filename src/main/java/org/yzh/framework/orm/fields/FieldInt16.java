package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;

public class FieldInt16 implements Schema<Integer> {

    public static final Schema INSTANCE = new FieldInt16();

    private FieldInt16() {
    }

    @Override
    public Integer readFrom(ByteBuf input) {
        return input.readUnsignedShort();
    }

    @Override
    public void writeTo(ByteBuf output, Integer value) {
        output.writeShort(value);
    }
}
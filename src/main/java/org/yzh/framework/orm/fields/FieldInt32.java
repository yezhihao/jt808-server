package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;

public class FieldInt32 implements Schema<Integer> {

    public static final Schema INSTANCE = new FieldInt32();

    private FieldInt32() {
    }

    @Override
    public Integer readFrom(ByteBuf input) {
        return (int) input.readUnsignedInt();
    }

    @Override
    public void writeTo(ByteBuf output, Integer value) {
        output.writeInt(value);
    }
}
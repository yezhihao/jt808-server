package org.yzh.framework.orm.fields;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;

public class FieldLong32 implements Schema<Long> {

    public static final Schema INSTANCE = new FieldLong32();

    private FieldLong32() {
    }

    @Override
    public Long readFrom(ByteBuf input) {
        return input.readUnsignedInt();
    }

    @Override
    public void writeTo(ByteBuf output, Long value) {
        output.writeInt(value.intValue());
    }
}
package org.yzh.framework.orm.schema;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;

public class LongSchema {

    public static class Long32 implements Schema<Long> {
        public static final Schema INSTANCE = new Long32();

        private Long32() {
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

    public static class Long64 implements Schema<Long> {
        public static final Schema INSTANCE = new Long64();

        private Long64() {
        }

        @Override
        public Long readFrom(ByteBuf input) {
            return input.readLong();
        }

        @Override
        public void writeTo(ByteBuf output, Long value) {
            output.writeLong(value.intValue());
        }
    }
}
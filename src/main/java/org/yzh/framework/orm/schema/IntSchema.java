package org.yzh.framework.orm.schema;

import io.netty.buffer.ByteBuf;
import org.yzh.framework.orm.Schema;

public class IntSchema {

    public static class Int8 implements Schema<Integer> {
        public static final Schema INSTANCE = new Int8();

        private Int8() {
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

    public static class Int16 implements Schema<Integer> {
        public static final Schema INSTANCE = new Int16();

        private Int16() {
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

    public static class Int32 implements Schema<Integer> {
        public static final Schema INSTANCE = new Int32();

        private Int32() {
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
}
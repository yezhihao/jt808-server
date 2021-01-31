package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;

/**
 * 行驶记录功能获取的速度，WORD，1/10km/h
 * length 2
 */
public class Speed {

    public static final int id = 0x03;

    public static int id() {
        return id;
    }

    private int value;

    public Speed() {
    }

    public Speed(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static class Schema implements io.github.yezhihao.protostar.Schema<Speed> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public Speed readFrom(ByteBuf input) {
            Speed message = new Speed();
            message.value = input.readUnsignedShort();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, Speed message) {
            output.writeShort(message.value);
        }
    }
}
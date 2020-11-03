package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 行驶记录功能获取的速度，WORD，1/10km/h
 * length 2
 */
public class Speed extends Attribute {

    public static final int attributeId = 0x03;
    private int value;

    public Speed() {
    }

    public Speed(int value) {
        this.value = value;
    }

    public int getAttributeId() {
        return attributeId;
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
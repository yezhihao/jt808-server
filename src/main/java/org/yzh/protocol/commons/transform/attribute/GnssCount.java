package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * GNSS 定位卫星数
 * length 1 BYTE
 */
public class GnssCount extends Attribute {

    public static final int attributeId = 0x31;
    private int value;

    public GnssCount() {
    }

    public GnssCount(int value) {
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

    public static class Schema implements io.github.yezhihao.protostar.Schema<GnssCount> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public GnssCount readFrom(ByteBuf input) {
            GnssCount message = new GnssCount();
            message.value = input.readUnsignedByte();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, GnssCount message) {
            output.writeByte(message.value);
        }
    }
}
package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 无线通信网络信号强度
 * length 1 BYTE
 */
public class SignalStrength extends Attribute {

    public static final int attributeId = 0x30;
    private int value;

    public SignalStrength() {
    }

    public SignalStrength(int value) {
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

    public static class Schema implements io.github.yezhihao.protostar.Schema<SignalStrength> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public SignalStrength readFrom(ByteBuf input) {
            SignalStrength message = new SignalStrength();
            message.value = input.readUnsignedByte();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, SignalStrength message) {
            output.writeByte(message.value);
        }
    }
}
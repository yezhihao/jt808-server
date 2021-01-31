package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;

/**
 * 无线通信网络信号强度
 * length 1 BYTE
 */
public class SignalStrength {

    public static final int id = 0x30;

    public static int id() {
        return id;
    }

    private int value;

    public SignalStrength() {
    }

    public SignalStrength(int value) {
        this.value = value;
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
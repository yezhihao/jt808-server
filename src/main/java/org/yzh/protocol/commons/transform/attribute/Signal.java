package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;

/**
 * 扩展车辆信号状态位，定义见表 31
 * length 4
 */
public class Signal {

    public static final int id = 0x25;

    public static int id() {
        return id;
    }

    private int value;

    public Signal() {
    }

    public Signal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static class Schema implements io.github.yezhihao.protostar.Schema<Signal> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public Signal readFrom(ByteBuf input) {
            Signal message = new Signal();
            message.value = (int) input.readUnsignedInt();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, Signal message) {
            output.writeInt(message.value);
        }
    }
}
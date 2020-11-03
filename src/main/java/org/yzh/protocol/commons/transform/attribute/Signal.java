package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 扩展车辆信号状态位，定义见表 31
 * length 4
 */
public class Signal extends Attribute {

    public static final int attributeId = 0x25;
    private int value;

    public Signal() {
    }

    public Signal(int value) {
        this.value = value;
    }

    @Override
    public int getAttributeId() {
        return attributeId;
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
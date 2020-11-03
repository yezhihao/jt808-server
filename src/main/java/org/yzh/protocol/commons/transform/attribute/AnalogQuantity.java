package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 模拟量，bit0-15，AD0；bit16-31，AD1。
 * length 4
 */
public class AnalogQuantity extends Attribute {

    public static final int attributeId = 0x2b;
    private int value;

    public AnalogQuantity() {
    }

    public AnalogQuantity(int value) {
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

    public static class Schema implements io.github.yezhihao.protostar.Schema<AnalogQuantity> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public AnalogQuantity readFrom(ByteBuf input) {
            AnalogQuantity message = new AnalogQuantity();
            message.value = (int) input.readUnsignedInt();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, AnalogQuantity message) {
            output.writeInt(message.value);
        }
    }
}
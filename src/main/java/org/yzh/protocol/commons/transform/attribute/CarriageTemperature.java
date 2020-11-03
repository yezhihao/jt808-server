package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 车厢温度, 单位摄氏度
 * length 2
 */
public class CarriageTemperature extends Attribute {

    public static final int attributeId = 0x06;
    private int value;

    public CarriageTemperature() {
    }

    public CarriageTemperature(int value) {
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

    public static class Schema implements io.github.yezhihao.protostar.Schema<CarriageTemperature> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public CarriageTemperature readFrom(ByteBuf input) {
            CarriageTemperature message = new CarriageTemperature();
            message.value = input.readUnsignedShort();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, CarriageTemperature message) {
            output.writeShort(message.value);
        }
    }
}
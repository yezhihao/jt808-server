package org.yzh.protocol.commons.transform.attribute;

import org.yzh.framework.commons.transform.Bit;
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

    @Override
    public CarriageTemperature formBytes(byte... bytes) {
        this.value = (short) Bit.readInt16(bytes, 0);
        return this;
    }

    @Override
    public byte[] toBytes() {
        return Bit.write2Byte(new byte[2], 0, value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
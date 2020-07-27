package org.yzh.protocol.commons.additional.attribute;

import org.yzh.framework.commons.transform.Bit;
import org.yzh.protocol.commons.additional.Attribute;

/**
 * 车厢温度, 单位摄氏度
 * length 2
 */
public class CarriageTemperature extends Attribute {

    public static int attributeId;
    private int value;

    public CarriageTemperature() {
    }

    public CarriageTemperature(int value) {
        this.value = value;
    }

    public int getAttributeId() {
        return 0x06;
    }

    @Override
    public CarriageTemperature formBytes(byte... bytes) {
        //TODO
        this.value = Bit.readInt16(bytes, 0);
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
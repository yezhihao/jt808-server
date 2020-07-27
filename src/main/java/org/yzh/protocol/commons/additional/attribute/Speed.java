package org.yzh.protocol.commons.additional.attribute;

import org.yzh.framework.commons.transform.Bit;
import org.yzh.protocol.commons.additional.Attribute;

/**
 * 行驶记录功能获取的速度，WORD，1/10km/h
 * length 2
 */
public class Speed extends Attribute {

    public static int attributeId = 0x03;
    private int value;

    public Speed() {
    }

    public Speed(int value) {
        this.value = value;
    }

    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public Speed formBytes(byte... bytes) {
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
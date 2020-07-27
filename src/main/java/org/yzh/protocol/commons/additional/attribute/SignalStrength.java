package org.yzh.protocol.commons.additional.attribute;

import org.yzh.protocol.commons.additional.Attribute;

/**
 * 无线通信网络信号强度
 * length 1 BYTE
 */
public class SignalStrength extends Attribute {

    public static int attributeId = 0x30;
    private int value;

    public SignalStrength() {
    }

    public SignalStrength(int value) {
        this.value = value;
    }

    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public SignalStrength formBytes(byte... bytes) {
        this.value = bytes[0];
        return this;
    }

    @Override
    public byte[] toBytes() {
        return new byte[]{(byte) value};
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
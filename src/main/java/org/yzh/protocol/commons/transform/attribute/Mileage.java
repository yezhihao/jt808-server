package org.yzh.protocol.commons.transform.attribute;

import org.yzh.framework.commons.transform.Bytes;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 里程，DWORD，1/10km，对应车上里程表读数
 * length 4
 */
public class Mileage extends Attribute {

    public static final int attributeId = 0x01;
    private int value;

    public Mileage() {
    }

    public Mileage(int value) {
        this.value = value;
    }

    @Override
    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public Mileage formBytes(byte... bytes) {
        this.value = Bytes.getInt32(bytes, 0);
        return this;
    }

    @Override
    public byte[] toBytes() {
        return Bytes.setInt32(new byte[4], 0, value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
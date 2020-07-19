package org.yzh.web.jt808.dto.position.attribute;

import org.yzh.web.jt808.dto.position.Attribute;

/**
 * GNSS 定位卫星数
 * length 1 BYTE
 */
public class GnssCount implements Attribute {

    public static  int attributeId;
    private int value;

    public GnssCount() {
    }

    public GnssCount(int value) {
        this.value = value;
    }

    public int getAttributeId() {
        return 0x31;
    }

    @Override
    public GnssCount formBytes(byte... bytes) {
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
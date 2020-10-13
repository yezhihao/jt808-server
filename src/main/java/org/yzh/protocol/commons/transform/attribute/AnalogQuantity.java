package org.yzh.protocol.commons.transform.attribute;

import org.yzh.framework.commons.transform.Bytes;
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

    @Override
    public AnalogQuantity formBytes(byte... bytes) {
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
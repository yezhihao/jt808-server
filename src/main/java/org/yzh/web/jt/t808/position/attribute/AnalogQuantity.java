package org.yzh.web.jt.t808.position.attribute;

import org.yzh.framework.commons.transform.Bit;
import org.yzh.web.jt.t808.position.Attribute;

/**
 * 模拟量，bit0-15，AD0；bit16-31，AD1。
 * length 4
 */
public class AnalogQuantity extends Attribute {

    public static int attributeId = 0x2b;
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
        this.value = Bit.readInt32(bytes, 0);
        return this;
    }

    @Override
    public byte[] toBytes() {
        return Bit.write4Byte(new byte[4], 0, value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
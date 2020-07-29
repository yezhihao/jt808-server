package org.yzh.protocol.commons.additional.attribute;

import org.yzh.framework.commons.transform.Bit;
import org.yzh.protocol.commons.additional.Attribute;

/**
 * 扩展车辆信号状态位，定义见表 31
 * length 4
 */
public class Signal extends Attribute {

    public static final int attributeId = 0x25;
    private int value;

    public Signal() {
    }

    public Signal(int value) {
        this.value = value;
    }

    @Override
    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public Signal formBytes(byte... bytes) {
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
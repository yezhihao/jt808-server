package org.yzh.protocol.commons.transform.attribute;

import org.yzh.framework.commons.transform.Bytes;
import org.yzh.protocol.commons.transform.Attribute;

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
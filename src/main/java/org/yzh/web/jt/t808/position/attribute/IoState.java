package org.yzh.web.jt.t808.position.attribute;

import org.yzh.framework.commons.transform.Bit;
import org.yzh.web.jt.t808.position.Attribute;

/**
 * IO 状态位，定义见表 32
 * length 2
 */
public class IoState extends Attribute {

    public static int attributeId = 0x2a;
    private int value;

    public IoState() {
    }

    public IoState(int value) {
        this.value = value;
    }

    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public IoState formBytes(byte... bytes) {
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
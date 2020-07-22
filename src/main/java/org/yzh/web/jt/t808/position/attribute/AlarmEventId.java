package org.yzh.web.jt.t808.position.attribute;

import org.yzh.framework.commons.transform.Bit;
import org.yzh.web.jt.t808.position.Attribute;

/**
 * 需要人工确认报警事件的 ID，WORD，从 1 开始计数
 * length 2
 */
public class AlarmEventId extends Attribute {

    public static int attributeId = 0x04;
    private int value;

    public AlarmEventId() {
    }

    public AlarmEventId(int value) {
        this.value = value;
    }

    @Override
    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public AlarmEventId formBytes(byte... bytes) {
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
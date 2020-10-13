package org.yzh.protocol.commons.transform.attribute;

import org.yzh.framework.commons.transform.Bytes;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 里程
 * length 2 油量，WORD，1/10L，对应车上油量表读数
 */
public class Oil extends Attribute {

    public static final int attributeId = 0x02;
    private int value;

    public Oil() {
    }

    public Oil(int value) {
        this.value = value;
    }

    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public Oil formBytes(byte... bytes) {
        this.value = Bytes.getInt16(bytes, 0);
        return this;
    }

    @Override
    public byte[] toBytes() {
        return Bytes.setInt16(new byte[2], 0, value);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
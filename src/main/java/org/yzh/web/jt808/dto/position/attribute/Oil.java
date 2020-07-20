package org.yzh.web.jt808.dto.position.attribute;

import org.yzh.framework.commons.transform.Bit;
import org.yzh.web.jt808.dto.position.Attribute;

/**
 * 里程
 * length 2 油量，WORD，1/10L，对应车上油量表读数
 */
public class Oil extends Attribute {

    public static int attributeId = 0x02;
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
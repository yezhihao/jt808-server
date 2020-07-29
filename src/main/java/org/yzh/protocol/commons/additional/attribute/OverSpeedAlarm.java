package org.yzh.protocol.commons.additional.attribute;

import org.yzh.framework.commons.transform.Bit;
import org.yzh.protocol.commons.additional.Attribute;

/**
 * 超速报警附加信息见表 28
 * length 1 或 5
 */
public class OverSpeedAlarm extends Attribute {

    public static final int attributeId = 0x11;
    /** 位置类型 */
    private byte positionType;
    /** 区域或路段ID */
    private Integer areaId;

    public OverSpeedAlarm() {
    }

    public OverSpeedAlarm(byte positionType, Integer areaId) {
        this.positionType = positionType;
        this.areaId = areaId;
    }

    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public OverSpeedAlarm formBytes(byte[] bytes) {
        this.positionType = bytes[0];
        if (bytes.length >= 5)
            this.areaId = Bit.readInt32(bytes, 1);
        return this;
    }

    @Override
    public byte[] toBytes() {
        if (areaId == null)
            return new byte[]{positionType};

        byte[] bytes = new byte[5];
        bytes[0] = positionType;
        Bit.write4Byte(bytes, 1, areaId);
        return bytes;
    }

    public byte getPositionType() {
        return positionType;
    }

    public void setPositionType(byte positionType) {
        this.positionType = positionType;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}
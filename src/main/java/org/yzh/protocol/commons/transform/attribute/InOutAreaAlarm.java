package org.yzh.protocol.commons.transform.attribute;

import org.yzh.framework.commons.transform.Bytes;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 进出区域/路线报警附加信息见表 29
 * length 6
 */
public class InOutAreaAlarm extends Attribute {

    public static final int attributeId = 0x12;
    /** 位置类型 */
    private byte positionType;
    /** 区域或路段ID */
    private int areaId;
    /** 方向,0:进,1:出 */
    private byte direction;

    public InOutAreaAlarm() {
    }

    public InOutAreaAlarm(byte positionType, int areaId, byte direction) {
        this.positionType = positionType;
        this.areaId = areaId;
        this.direction = direction;
    }

    @Override
    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public InOutAreaAlarm formBytes(byte[] bytes) {
        this.positionType = bytes[0];
        this.areaId = Bytes.getInt32(bytes, 1);
        this.direction = bytes[5];
        return this;

    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[6];
        bytes[0] = this.positionType;
        Bytes.setInt32(bytes, 1, this.areaId);
        bytes[5] = this.direction;
        return bytes;
    }

    public byte getPositionType() {
        return positionType;
    }

    public void setPositionType(byte positionType) {
        this.positionType = positionType;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public byte getDirection() {
        return direction;
    }

    public void setDirection(byte direction) {
        this.direction = direction;
    }
}
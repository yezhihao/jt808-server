package org.yzh.web.jt808.dto.position.attribute;

import org.yzh.framework.commons.transform.Bit;
import org.yzh.web.jt808.dto.position.Attribute;

/**
 * 路段行驶时间不足/过长报警附加信息见表 30
 * length 7
 */
public class RouteDriveTimeAlarm implements Attribute {
    public static int attributeId = 0x13;
    /** 路段ID */
    private int routeId;
    /** 行驶时间,单位为秒(s) */
    private int driveTime;
    /** 结果,0: 不足,1:过长 */
    private byte result;

    public RouteDriveTimeAlarm() {
    }

    public RouteDriveTimeAlarm(int routeId, int driveTime, byte result) {
        this.routeId = routeId;
        this.driveTime = driveTime;
        this.result = result;
    }

    public int getAttributeId() {
        return attributeId;
    }

    @Override
    public RouteDriveTimeAlarm formBytes(byte[] bytes) {
        this.routeId = Bit.readInt32(bytes, 0);
        this.driveTime = Bit.readInt16(bytes, 4);
        this.result = bytes[6];
        return this;
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[7];
        Bit.write4Byte(bytes, 0, this.routeId);
        Bit.write4Byte(bytes, 4, this.driveTime);
        bytes[6] = this.result;
        return bytes;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getDriveTime() {
        return driveTime;
    }

    public void setDriveTime(int driveTime) {
        this.driveTime = driveTime;
    }

    public byte getResult() {
        return result;
    }

    public void setResult(byte result) {
        this.result = result;
    }
}
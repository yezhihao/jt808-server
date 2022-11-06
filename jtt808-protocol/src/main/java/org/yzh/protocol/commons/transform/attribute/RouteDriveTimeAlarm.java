package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.Schema;
import io.netty.buffer.ByteBuf;

/**
 * 路段行驶时间不足/过长 0x13
 * length 7
 */
public class RouteDriveTimeAlarm extends Alarm {

    public static final int key = 19;

    public static final Schema<RouteDriveTimeAlarm> SCHEMA = new RouteDriveTimeAlarmSchema();

    /** 路段ID */
    private int areaId;
    /** 路段行驶时间(秒) */
    private int driveTime;
    /** 结果：0.不足 1.过长 */
    private byte result;

    public RouteDriveTimeAlarm() {
    }

    public RouteDriveTimeAlarm(int areaId, int driveTime, byte result) {
        this.areaId = areaId;
        this.driveTime = driveTime;
        this.result = result;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
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

    @Override
    public int getAlarmType() {
        return key;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(80);
        sb.append("RouteDriveTimeAlarm{areaId=").append(areaId);
        sb.append(",driveTime=").append(driveTime);
        sb.append(",result=").append(result);
        sb.append('}');
        return sb.toString();
    }

    private static class RouteDriveTimeAlarmSchema implements Schema<RouteDriveTimeAlarm> {

        private RouteDriveTimeAlarmSchema() {
        }

        @Override
        public RouteDriveTimeAlarm readFrom(ByteBuf input) {
            RouteDriveTimeAlarm message = new RouteDriveTimeAlarm();
            message.areaId = input.readInt();
            message.driveTime = input.readUnsignedShort();
            message.result = input.readByte();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, RouteDriveTimeAlarm message) {
            output.writeInt(message.areaId);
            output.writeShort(message.driveTime);
            output.writeByte(message.result);
        }
    }
}
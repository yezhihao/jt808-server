package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;

/**
 * 路段行驶时间不足/过长报警附加信息见表 30
 * length 7
 */
public class RouteDriveTimeAlarm {

    public static final int id = 0x13;

    public static int id() {
        return id;
    }

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
    public String toString() {
        final StringBuilder sb = new StringBuilder(80);
        sb.append("RouteDriveTimeAlarm{areaId=").append(areaId);
        sb.append(",driveTime=").append(driveTime);
        sb.append(",result=").append(result);
        sb.append('}');
        return sb.toString();
    }

    public static class Schema implements io.github.yezhihao.protostar.Schema<RouteDriveTimeAlarm> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public RouteDriveTimeAlarm readFrom(ByteBuf input) {
            RouteDriveTimeAlarm message = new RouteDriveTimeAlarm();
            message.areaId = (int) input.readUnsignedInt();
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
package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.Schema;
import io.netty.buffer.ByteBuf;

/**
 * 超速报警 0x11
 * length 1 或 5
 */
public class OverSpeedAlarm {

    public static final int key = 17;

    public static final Schema<OverSpeedAlarm> SCHEMA = new OverSpeedAlarmSchema();

    /** 位置类型：0.无特定位置 1.圆形区域 2.矩形区域 3.多边形区域 4.路段 */
    private byte areaType;
    /** 区域或路段ID */
    private int areaId;

    public OverSpeedAlarm() {
    }

    public OverSpeedAlarm(byte areaType, int areaId) {
        this.areaType = areaType;
        this.areaId = areaId;
    }

    public byte getAreaType() {
        return areaType;
    }

    public void setAreaType(byte areaType) {
        this.areaType = areaType;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(80);
        sb.append("OverSpeedAlarm{areaType=").append(areaType);
        sb.append(",areaId=").append(areaId);
        sb.append('}');
        return sb.toString();
    }

    private static class OverSpeedAlarmSchema implements Schema<OverSpeedAlarm> {

        private OverSpeedAlarmSchema() {
        }

        @Override
        public OverSpeedAlarm readFrom(ByteBuf input) {
            OverSpeedAlarm message = new OverSpeedAlarm();
            message.areaType = input.readByte();
            if (message.areaType > 0)
                message.areaId = input.readInt();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, OverSpeedAlarm message) {
            output.writeByte(message.areaType);
            if (message.areaType > 0)
                output.writeInt(message.areaId);
        }
    }
}
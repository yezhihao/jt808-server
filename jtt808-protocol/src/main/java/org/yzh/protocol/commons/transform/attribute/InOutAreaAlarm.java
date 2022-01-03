package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.Schema;
import io.netty.buffer.ByteBuf;

/**
 * 进出区域/路线报警 0x12
 * length 6
 */
public class InOutAreaAlarm {

    public static final Schema<InOutAreaAlarm> SCHEMA = new InOutAreaAlarmSchema();

    public static final int id = 18;

    public static int id() {
        return id;
    }

    /** 位置类型：1.圆形区域 2.矩形区域 3.多边形区域 4.路线 */
    private byte areaType;
    /** 区域或路段ID */
    private int areaId;
    /** 方向：0.进 1.出 */
    private byte direction;

    public InOutAreaAlarm() {
    }

    public InOutAreaAlarm(byte areaType, int areaId, byte direction) {
        this.areaType = areaType;
        this.areaId = areaId;
        this.direction = direction;
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

    public byte getDirection() {
        return direction;
    }

    public void setDirection(byte direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(80);
        sb.append("InOutAreaAlarm{areaType=").append(areaType);
        sb.append(",areaId=").append(areaId);
        sb.append(",direction=").append(direction);
        sb.append('}');
        return sb.toString();
    }

    private static class InOutAreaAlarmSchema implements Schema<InOutAreaAlarm> {

        private InOutAreaAlarmSchema() {
        }

        @Override
        public InOutAreaAlarm readFrom(ByteBuf input) {
            InOutAreaAlarm message = new InOutAreaAlarm();
            message.areaType = input.readByte();
            message.areaId = input.readInt();
            message.direction = input.readByte();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, InOutAreaAlarm message) {
            output.writeByte(message.areaType);
            output.writeInt(message.areaId);
            output.writeByte(message.direction);
        }
    }
}
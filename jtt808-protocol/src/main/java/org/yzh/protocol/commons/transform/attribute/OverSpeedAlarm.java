package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;

/**
 * 超速报警附加信息见表 28
 * length 1 或 5
 */
public class OverSpeedAlarm {

    public static final int id = 0x11;

    public static int id() {
        return id;
    }

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

    public static class Schema implements io.github.yezhihao.protostar.Schema<OverSpeedAlarm> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
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
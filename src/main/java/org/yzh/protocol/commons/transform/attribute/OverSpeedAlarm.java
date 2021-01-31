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

    public static class Schema implements io.github.yezhihao.protostar.Schema<OverSpeedAlarm> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public OverSpeedAlarm readFrom(ByteBuf input) {
            OverSpeedAlarm message = new OverSpeedAlarm();
            message.positionType = input.readByte();
            if (message.positionType > 0)
                message.areaId = (int) input.readUnsignedInt();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, OverSpeedAlarm message) {
            output.writeByte(message.positionType);
            if (message.positionType > 0)
                output.writeInt(message.areaId);
        }
    }
}
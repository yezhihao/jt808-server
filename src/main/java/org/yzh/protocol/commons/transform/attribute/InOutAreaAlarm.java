package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;

/**
 * 进出区域/路线报警附加信息见表 29
 * length 6
 */
public class InOutAreaAlarm {

    public static final int id = 0x12;

    public static int id() {
        return id;
    }

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

    public static class Schema implements io.github.yezhihao.protostar.Schema<InOutAreaAlarm> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public InOutAreaAlarm readFrom(ByteBuf input) {
            InOutAreaAlarm message = new InOutAreaAlarm();
            message.positionType = input.readByte();
            message.areaId = (int) input.readUnsignedInt();
            message.direction = input.readByte();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, InOutAreaAlarm message) {
            output.writeByte(message.positionType);
            output.writeInt(message.areaId);
            output.writeByte(message.direction);
        }
    }
}
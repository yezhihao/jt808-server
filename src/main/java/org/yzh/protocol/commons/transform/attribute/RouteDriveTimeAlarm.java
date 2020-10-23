package org.yzh.protocol.commons.transform.attribute;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.commons.transform.Attribute;

/**
 * 路段行驶时间不足/过长报警附加信息见表 30
 * length 7
 */
public class RouteDriveTimeAlarm extends Attribute {

    public static final int attributeId = 0x13;
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

    public static class Schema implements org.yzh.framework.orm.Schema<RouteDriveTimeAlarm> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public RouteDriveTimeAlarm readFrom(ByteBuf input) {
            RouteDriveTimeAlarm message = new RouteDriveTimeAlarm();
            message.routeId = (int) input.readUnsignedInt();
            message.driveTime = input.readUnsignedShort();
            message.result = input.readByte();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, RouteDriveTimeAlarm message) {
            output.writeInt(message.routeId);
            output.writeShort(message.driveTime);
            output.writeByte(message.result);
        }
    }
}
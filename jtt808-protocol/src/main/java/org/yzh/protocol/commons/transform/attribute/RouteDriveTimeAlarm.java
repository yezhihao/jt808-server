package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.Schema;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 路段行驶时间不足/过长 0x13
 * length 7
 */
@ToString
@Data
@Accessors(chain = true)
public class RouteDriveTimeAlarm extends Alarm {

    public static final Integer key = 19;

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

    @Override
    public int getAlarmType() {
        return key;
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
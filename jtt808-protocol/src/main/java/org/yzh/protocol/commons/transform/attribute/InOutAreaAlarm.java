package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.Schema;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 进出区域/路线报警 0x12
 * length 6
 */
@ToString
@Data
@Accessors(chain = true)
public class InOutAreaAlarm extends Alarm {

    public static final Integer key = 18;

    public static final Schema<InOutAreaAlarm> SCHEMA = new InOutAreaAlarmSchema();

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

    @Override
    public int getAlarmType() {
        return key;
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
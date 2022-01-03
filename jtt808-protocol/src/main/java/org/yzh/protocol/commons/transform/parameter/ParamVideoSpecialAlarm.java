package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.annotation.Field;
import io.netty.buffer.ByteBuf;

/**
 * 特殊报警录像参数设置
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class ParamVideoSpecialAlarm {

    public static final Schema<ParamVideoSpecialAlarm> SCHEMA = new ParamVideoSpecialAlarmSchema();

    public static final int id = 0x0079;

    public static int id() {
        return id;
    }

    @Field(desc = "特殊报警录像存储阈值(占用主存储器存储阈值百分比,取值1~99.默认值为20)")
    private byte storageThreshold;
    @Field(desc = "特殊报警录像持续时间,特殊报警录像的最长持续时间(分钟),默认值为5")
    private byte duration;
    @Field(desc = "特殊报警标识起始时间,特殊报警发生前进行标记的录像时间(分钟),默认值为1")
    private byte startTime;

    public byte getStorageThreshold() {
        return storageThreshold;
    }

    public void setStorageThreshold(byte storageThreshold) {
        this.storageThreshold = storageThreshold;
    }

    public byte getDuration() {
        return duration;
    }

    public void setDuration(byte duration) {
        this.duration = duration;
    }

    public byte getStartTime() {
        return startTime;
    }

    public void setStartTime(byte startTime) {
        this.startTime = startTime;
    }

    private static class ParamVideoSpecialAlarmSchema implements Schema<ParamVideoSpecialAlarm> {

        private ParamVideoSpecialAlarmSchema() {
        }

        @Override
        public ParamVideoSpecialAlarm readFrom(ByteBuf input) {
            ParamVideoSpecialAlarm message = new ParamVideoSpecialAlarm();
            message.storageThreshold = input.readByte();
            message.duration = input.readByte();
            message.startTime = input.readByte();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, ParamVideoSpecialAlarm message) {
            output.writeByte(message.storageThreshold);
            output.writeByte(message.duration);
            output.writeByte(message.startTime);
        }
    }
}
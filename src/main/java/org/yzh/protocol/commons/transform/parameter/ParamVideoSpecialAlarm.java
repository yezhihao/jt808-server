package org.yzh.protocol.commons.transform.parameter;

import io.netty.buffer.ByteBuf;

/**
 * 特殊报警录像参数设置
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ParamVideoSpecialAlarm {

    public static final int id = 0x0079;

    public static int id() {
        return id;
    }

    private byte storageThreshold;
    private byte duration;
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

    public static class Schema implements io.github.yezhihao.protostar.Schema<ParamVideoSpecialAlarm> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
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
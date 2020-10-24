package org.yzh.protocol.commons.transform.parameter;

import io.netty.buffer.ByteBuf;

/**
 * 图像分析报警参数设置
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ParamImageIdentifyAlarm {

    public static final int id = 0x007B;

    public static int id() {
        return id;
    }

    //车辆核载人数
    private byte overloadThreshold;
    //疲劳程度阈值
    private byte fatigueThreshold;

    public ParamImageIdentifyAlarm() {
    }

    public byte getOverloadThreshold() {
        return overloadThreshold;
    }

    public void setOverloadThreshold(byte overloadThreshold) {
        this.overloadThreshold = overloadThreshold;
    }

    public byte getFatigueThreshold() {
        return fatigueThreshold;
    }

    public void setFatigueThreshold(byte fatigueThreshold) {
        this.fatigueThreshold = fatigueThreshold;
    }

    public static class Schema implements org.yzh.framework.orm.Schema<ParamImageIdentifyAlarm> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public ParamImageIdentifyAlarm readFrom(ByteBuf input) {
            ParamImageIdentifyAlarm message = new ParamImageIdentifyAlarm();
            message.overloadThreshold = input.readByte();
            message.fatigueThreshold = input.readByte();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, ParamImageIdentifyAlarm message) {
            output.writeByte(message.overloadThreshold);
            output.writeByte(message.fatigueThreshold);
        }
    }
}
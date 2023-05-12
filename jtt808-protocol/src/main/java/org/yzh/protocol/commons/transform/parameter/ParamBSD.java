package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.annotation.Field;

/**
 * 盲区监测系统参数
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class ParamBSD {

    public static final Integer key = 0xF367;

    @Field(length = 1, desc = "后方接近报警时间阈值")
    private byte rearApproachAlarmTimeThreshold = -1;
    @Field(length = 1, desc = "侧后方接近报警时间阈值")
    private byte rearSideApproachAlarmTimeThreshold = -1;

    public byte getRearApproachAlarmTimeThreshold() {
        return rearApproachAlarmTimeThreshold;
    }

    public void setRearApproachAlarmTimeThreshold(byte rearApproachAlarmTimeThreshold) {
        this.rearApproachAlarmTimeThreshold = rearApproachAlarmTimeThreshold;
    }

    public byte getRearSideApproachAlarmTimeThreshold() {
        return rearSideApproachAlarmTimeThreshold;
    }

    public void setRearSideApproachAlarmTimeThreshold(byte rearSideApproachAlarmTimeThreshold) {
        this.rearSideApproachAlarmTimeThreshold = rearSideApproachAlarmTimeThreshold;
    }

    @Override
    public String toString() {
        return "ParamBSD{" +
                "rearApproachAlarmTimeThreshold=" + rearApproachAlarmTimeThreshold +
                ", rearSideApproachAlarmTimeThreshold=" + rearSideApproachAlarmTimeThreshold +
                '}';
    }
}

package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.annotation.Field;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 盲区监测系统参数
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class ParamBSD {

    public static final Integer key = 0xF367;

    @Field(desc = "后方接近报警时间阈值")
    private byte rearApproachAlarmTimeThreshold = -1;
    @Field(desc = "侧后方接近报警时间阈值")
    private byte rearSideApproachAlarmTimeThreshold = -1;

}

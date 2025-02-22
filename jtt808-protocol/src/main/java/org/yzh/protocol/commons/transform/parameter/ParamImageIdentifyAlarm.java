package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.annotation.Field;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 图像分析报警参数设置
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class ParamImageIdentifyAlarm {

    public static final Integer key = 0x007B;

    @Field(length = 1, desc = "车辆核载人数,客运车辆核定载客人数,视频分析结果超过时产生报警")
    private byte overloadThreshold;
    @Field(length = 1, desc = "疲劳程度阈值,视频分析疲劳驾驶报警阈值,超过时产生报警")
    private byte fatigueThreshold;

}
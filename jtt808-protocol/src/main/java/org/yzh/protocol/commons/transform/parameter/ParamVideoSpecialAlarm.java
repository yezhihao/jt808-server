package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.annotation.Field;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 特殊报警录像参数设置
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class ParamVideoSpecialAlarm {

    public static final Integer key = 0x0079;

    @Field(length = 1, desc = "特殊报警录像存储阈值(占用主存储器存储阈值百分比,取值1~99.默认值为20)")
    private byte storageThreshold;
    @Field(length = 1, desc = "特殊报警录像持续时间,特殊报警录像的最长持续时间(分钟),默认值为5")
    private byte duration;
    @Field(length = 1, desc = "特殊报警标识起始时间,特殊报警发生前进行标记的录像时间(分钟),默认值为1")
    private byte startTime;

}
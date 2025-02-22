package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.annotation.Field;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalTime;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
public class TimeRange {

    @Field(length = 2, charset = "BCD", desc = "开始时间")
    private LocalTime startTime;
    @Field(length = 2, charset = "BCD", desc = "结束时间")
    private LocalTime endTime;

}
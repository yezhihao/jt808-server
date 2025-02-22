package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 * 该消息2019版本已删除
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.提问下发)
public class T8302 extends JTMessage {

    @Field(length = 1, desc = "标志：" +
            " [0]紧急" +
            " [1]保留" +
            " [2]终端显示器显示" +
            " [3]终端 TTS 播读" +
            " [4]广告屏显示" +
            " [5]0.中心导航信息|1.CAN故障码信息" +
            " [6~7]保留")
    private int sign;
    @Field(lengthUnit = 1, desc = "问题")
    private String content;
    @Field(desc = "候选答案列表")
    private List<Option> options;

    @ToString
    @Data
    @Accessors(chain = true)
    public static class Option {
        @Field(length = 1, desc = "答案ID")
        private int id;
        @Field(lengthUnit = 2, desc = "答案内容")
        private String content;
    }
}
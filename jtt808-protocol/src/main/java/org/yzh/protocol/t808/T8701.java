package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.行驶记录仪参数下传命令)
public class T8701 extends JTMessage {

    @Field(length = 1, desc = "命令字：" +
            " 130.设置车辆信息" +
            " 131.设置记录仪初次安装日期" +
            " 132.设置状态童配置信息" +
            " 194.设置记录仪时间" +
            " 195.设置记录仪脉冲系数" +
            " 196.设置初始里程" +
            " 197~207.预留")
    private int type;
    @Field(desc = "数据块(参考GB/T 19056)")
    private byte[] content;

}
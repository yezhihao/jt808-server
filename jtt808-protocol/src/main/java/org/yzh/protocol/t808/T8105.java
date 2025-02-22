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
@Message(JT808.终端控制)
public class T8105 extends JTMessage {

    @Field(length = 1, desc = "命令字：" +
            " 1.无线升级" +
            " 2.控制终端连接指定服务器" +
            " 3.终端关机" +
            " 4.终端复位" +
            " 5.终端恢复出厂设置" +
            " 6.关闭数据通信" +
            " 7.关闭所有无线通信")
    private int command;
    @Field(desc = "命令参数")
    private String parameter;

}
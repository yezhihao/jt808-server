package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.行驶记录仪参数下传命令)
public class T8701 extends JTMessage {

    @Field(index = 0, type = DataType.BYTE, desc = "命令字：" +
            " 130.设置车辆信息" +
            " 131.设置记录仪初次安装日期" +
            " 132.设置状态童配置信息" +
            " 194.设置记录仪时间" +
            " 195.设置记录仪脉冲系数" +
            " 196.设置初始里程" +
            " 197~207.预留")
    private int type;
    @Field(index = 1, type = DataType.BYTES, desc = "数据块(参考GB/T 19056)")
    private byte[] content;

    public T8701() {
    }

    public T8701(int type, byte[] content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
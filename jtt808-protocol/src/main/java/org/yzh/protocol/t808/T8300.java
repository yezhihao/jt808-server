package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.Bit;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.文本信息下发)
public class T8300 extends JTMessage {

    @Field(length = 1, desc = "标志：" +
            " [0]紧急" +
            " [1]保留" +
            " [2]终端显示器显示" +
            " [3]终端 TTS 播读" +
            " [4]广告屏显示" +
            " [5]0.中心导航信息|1.CAN故障码信息" +
            " [6~7]保留")
    private int sign;
    @Field(length = 1, desc = "类型：1.通知 2.服务", version = 1)
    private int type;
    @Field(desc = "文本信息", version = 0)
    @Field(desc = "文本信息", version = 1)
    private String content;

    public T8300() {
    }

    public T8300(String content, int... sign) {
        this.content = content;
        this.sign = Bit.writeInt(sign);
    }

    public T8300(int type, String content, int... sign) {
        this.type = type;
        this.content = content;
        this.sign = Bit.writeInt(sign);
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
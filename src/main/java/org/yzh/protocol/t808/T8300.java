package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.Bin;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.文本信息下发)
public class T8300 extends JTMessage {

    private int sign;
    private int type;
    private String content;

    public T8300() {
    }

    public T8300(String content, int... sign) {
        this.content = content;
        this.sign = Bin.writeInt(sign);
    }

    public T8300(int type, String content, int... sign) {
        this.type = type;
        this.content = content;
        this.sign = Bin.writeInt(sign);
    }

    @Field(index = 0, type = DataType.BYTE, desc = "标志:\n" +
            "0_紧急\n" +
            "1_保留\n" +
            "2_终端显示器显示\n" +
            "3_终端 TTS 播读\n" +
            "4_广告屏显示\n" +
            "5_中心导航信息，1.CAN 故障码信息\n" +
            "6-7_保留")
    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "类型: 1.通知 2.服务", version = 1)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.STRING, desc = "文本信息", version = 0)
    @Field(index = 2, type = DataType.STRING, desc = "文本信息", version = 1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Fs;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.Bin;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.文本信息下发)
public class T8300 extends JTMessage {
    /**
     * 0 1.紧急
     * 1    保留
     * 2 1.终端显示器显示
     * 3 1.终端 TTS 播读
     * 4 1.广告屏显示
     * 5 0.中心导航信息，1.CAN 故障码信息
     * 6-7  保留
     */
    private int sign;
    /**
     * 1.通知
     * 2.服务
     */
    private int type;
    private String content;

    public T8300() {
    }

    public T8300(String mobileNo) {
        super(new Header(mobileNo, JT808.文本信息下发));
    }

    @Field(index = 0, type = DataType.BYTE, desc = "标志")
    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public void setSign(int... sign) {
        this.sign = Bin.writeInt(sign);
    }

    @Field(index = 1, type = DataType.BYTE, desc = "类型", version = 1)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Fs({@Field(index = 1, type = DataType.STRING, desc = "文本信息", version = 0),
            @Field(index = 2, type = DataType.STRING, desc = "文本信息", version = 1)})
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
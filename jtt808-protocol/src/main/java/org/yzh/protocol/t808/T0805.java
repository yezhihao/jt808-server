package org.yzh.protocol.t808;

import io.github.yezhihao.netmc.core.model.Response;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.摄像头立即拍摄命令应答)
public class T0805 extends JTMessage implements Response {

    @Field(length = 2, desc = "应答流水号")
    private int responseSerialNo;
    @Field(length = 1, desc = "结果：0.成功 1.失败 2.通道不支持")
    private int result;
    @Field(totalUnit = 2, desc = "多媒体ID列表")
    private int[] id;

    public int getResponseSerialNo() {
        return responseSerialNo;
    }

    public void setResponseSerialNo(int responseSerialNo) {
        this.responseSerialNo = responseSerialNo;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
    }
}
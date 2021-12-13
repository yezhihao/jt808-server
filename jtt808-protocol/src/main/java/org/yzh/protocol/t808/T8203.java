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
@Message(JT808.人工确认报警消息)
public class T8203 extends JTMessage implements Response {

    @Field(length = 2, desc = "消息流水号")
    private int responseSerialNo;
    @Field(length = 4, desc = "报警类型：" +
            " [0]确认紧急报警" +
            " [1~2]保留" +
            " [3]确认危险预警" +
            " [4~19]保留" +
            " [20]确认进出区域报警" +
            " [21]确认进出路线报警" +
            " [22]确认路段行驶时间不足/过长报警" +
            " [23~26]保留" +
            " [27]确认车辆非法点火报警" +
            " [28]确认车辆非法位移报警" +
            " [29~31]保留")
    private int type;

    public T8203() {
    }

    public T8203(int responseSerialNo, int type) {
        this.responseSerialNo = responseSerialNo;
        this.type = type;
    }

    public int getResponseSerialNo() {
        return responseSerialNo;
    }

    public void setResponseSerialNo(int responseSerialNo) {
        this.responseSerialNo = responseSerialNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
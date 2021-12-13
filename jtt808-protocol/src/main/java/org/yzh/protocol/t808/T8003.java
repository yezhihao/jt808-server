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
@Message({JT808.服务器补传分包请求, JT808.终端补传分包请求})
public class T8003 extends JTMessage implements Response {

    @Field(length = 2, desc = "原始消息流水号")
    private int responseSerialNo;
    @Field(length = 1, desc = "重传包总数", version = {-1, 0})
    @Field(length = 2, desc = "重传包总数", version = 1)
    private int total;
    @Field(desc = "重传包ID列表")
    private short[] id;

    public int getResponseSerialNo() {
        return responseSerialNo;
    }

    public void setResponseSerialNo(int responseSerialNo) {
        this.responseSerialNo = responseSerialNo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public short[] getId() {
        return id;
    }

    public void setId(short[] id) {
        this.id = id;
        this.total = id.length;
    }
}
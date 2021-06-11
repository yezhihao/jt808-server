package org.yzh.protocol.t808;

import io.github.yezhihao.netmc.core.model.Response;
import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message({JT808.终端补传分包请求, JT808.服务器补传分包请求})
public class T8003 extends JTMessage implements Response {

    private int responseSerialNo;
    private int total;
    private byte[] items;

    @Field(index = 0, type = DataType.WORD, desc = "原始消息流水号")
    public int getResponseSerialNo() {
        return responseSerialNo;
    }

    public void setResponseSerialNo(int responseSerialNo) {
        this.responseSerialNo = responseSerialNo;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "重传包总数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 5, type = DataType.BYTES, desc = "重传包ID列表")
    public byte[] getItems() {
        return items;
    }

    public void setItems(byte[] items) {
        this.items = items;
        this.total = items.length;
    }
}
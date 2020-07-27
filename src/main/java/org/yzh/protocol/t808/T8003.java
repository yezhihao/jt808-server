package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.补传分包请求)
public class T8003 extends AbstractMessage<Header> {

    private Integer serialNo;
    private Integer total;
    private byte[] items;

    public T8003() {
    }

    @Field(index = 0, type = DataType.WORD, desc = "原始消息流水号")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "重传包总数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
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
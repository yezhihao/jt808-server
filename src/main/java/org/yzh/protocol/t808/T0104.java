package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.TerminalParameter;
import org.yzh.protocol.commons.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.查询终端参数应答)
public class T0104 extends AbstractMessage<Header> {

    private int serialNo;
    private int total;
    private List<TerminalParameter> items;

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 2, type = DataType.BYTE, desc = "应答参数个数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 3, type = DataType.LIST, desc = "参数项列表")
    public List<TerminalParameter> getItems() {
        return items;
    }

    public void setItems(List<TerminalParameter> items) {
        this.items = items;
        this.total = items.size();
    }

    public void addItem(TerminalParameter item) {
        if (items == null)
            items = new ArrayList<>();
        items.add(item);
        total = items.size();
    }
}
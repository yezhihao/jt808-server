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
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.设置终端参数)
public class T8103 extends AbstractMessage<Header> {

    private Integer total;
    private List<TerminalParameter> items;

    public T8103() {
    }

    public T8103(String mobileNo) {
        super(new Header(mobileNo, JT808.设置终端参数));
    }

    @Field(index = 0, type = DataType.BYTE, desc = "参数总数")
    public Integer getTotal() {
        if (items == null || items.isEmpty())
            return 0;
        return items.size();
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Field(index = 1, type = DataType.LIST, desc = "参数项列表")
    public List<TerminalParameter> getItems() {
        return items;
    }

    public void setItems(List<TerminalParameter> items) {
        this.items = items;
    }

    public void addItem(TerminalParameter item) {
        if (items == null)
            items = new ArrayList<>();
        items.add(item);
    }
}
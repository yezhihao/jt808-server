package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 * 该消息2019版本已删除
 */
@Message(JT808.信息点播菜单设置)
public class T8303 extends AbstractMessage<Header> {

    /** @see org.yzh.protocol.commons.Action */
    private int type;
    private int total;
    private List<Item> items;

    @Field(index = 0, type = DataType.BYTE, desc = "设置类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "设置总数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 2, type = DataType.LIST, desc = "事件项列表")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(int id, String content) {
        if (this.items == null)
            this.items = new ArrayList();
        this.items.add(new Item(id, content));
        this.total = items.size();
    }

    public static class Item {
        private int id;
        private String content;

        public Item() {
        }

        public Item(int id, String content) {
            this.id = id;
            this.content = content;
        }

        @Field(index = 0, type = DataType.BYTE, desc = "事件ID")
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Field(index = 3, type = DataType.STRING, lengthSize = 2, desc = "信息名称")
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
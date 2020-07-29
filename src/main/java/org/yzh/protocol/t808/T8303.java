package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.Charsets;
import org.yzh.protocol.commons.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.信息点播菜单设置)
public class T8303 extends AbstractMessage<Header> {

    /** @see org.yzh.protocol.commons.Action */
    private Integer type;
    private Integer total;
    private List<Item> items;

    @Field(index = 0, type = DataType.BYTE, desc = "设置类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "设置总数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
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
        private Integer id;
        private Integer length;
        private String content;

        public Item() {
        }

        public Item(Integer id, String content) {
            this.id = id;
            this.content = content;
            this.length = content.getBytes(Charsets.GBK).length;
        }

        @Field(index = 0, type = DataType.BYTE, desc = "事件ID")
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Field(index = 1, type = DataType.WORD, desc = "长度")
        public Integer getLength() {
            if (length == null)
                this.length = content.getBytes(Charsets.GBK).length;
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        @Field(index = 3, type = DataType.STRING, lengthName = "length", desc = "信息名称")
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
            this.length = content.getBytes(Charsets.GBK).length;
        }
    }
}
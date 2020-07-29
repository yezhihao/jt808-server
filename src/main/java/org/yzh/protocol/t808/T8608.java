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
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.查询区域或线路数据)
public class T8608 extends AbstractMessage<Header> {

    /** @see org.yzh.protocol.commons.Shape */
    private Integer type;
    private Integer total;
    private List<Item> items;

    @Field(index = 0, type = DataType.BYTE, desc = "查询类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Field(index = 0, type = DataType.BYTE, desc = "区域总数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Field(index = 1, type = DataType.LIST, desc = "区域列表")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.total = items.size();
    }

    public void addItem(Integer id) {
        if (items == null)
            items = new ArrayList();
        items.add(new Item(id));
        total = items.size();
    }

    public static class Item {
        private Integer id;

        public Item() {
        }

        public Item(Integer id) {
            this.id = id;
        }

        @Field(index = 0, type = DataType.DWORD, desc = "区域ID")
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
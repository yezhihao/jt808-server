package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.查询区域或线路数据)
public class T8608 extends JTMessage {

    /** @see org.yzh.protocol.commons.Shape */
    private int type;
    private int total;
    private List<Item> items;

    public T8608() {
    }

    public T8608(String mobileNo) {
        super(new Header(mobileNo, JT808.查询区域或线路数据));
    }

    @Field(index = 0, type = DataType.BYTE, desc = "查询类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 0, type = DataType.BYTE, desc = "区域总数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
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

    public void addItem(int id) {
        if (items == null)
            items = new ArrayList();
        items.add(new Item(id));
        total = items.size();
    }

    public static class Item {
        private int id;

        public Item() {
        }

        public Item(int id) {
            this.id = id;
        }

        @Field(index = 0, type = DataType.DWORD, desc = "区域ID")
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
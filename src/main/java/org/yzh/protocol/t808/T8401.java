package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.设置电话本)
public class T8401 extends JTMessage {

    /** @see org.yzh.protocol.commons.Action */
    private int type;
    private int total;
    private List<Item> items;

    @Field(index = 0, type = DataType.BYTE, desc = "类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "总数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 2, type = DataType.LIST, desc = "联系人列表")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void add(Item item) {
        if (items == null)
            items = new ArrayList();
        items.add(item);
        total = items.size();
    }

    public static class Item {
        private int sign;
        private String phone;
        private String name;

        public Item() {
        }

        public Item(int sign, String phone, String name) {
            this.sign = sign;
            this.phone = phone;
            this.name = name;
        }

        @Field(index = 0, type = DataType.BYTE, desc = "标志")
        public int getSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }

        @Field(index = 2, type = DataType.STRING, lengthSize = 1, desc = "电话号码")
        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Field(index = 3, type = DataType.STRING, lengthSize = 1, desc = "联系人")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
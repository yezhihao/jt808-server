package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.定位数据批量上传)
public class T0704 extends AbstractMessage<Header> {

    private int total;
    private int type;
    private List<Item> items;

    @Field(index = 0, type = DataType.WORD, desc = "数据项个数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 2, type = DataType.BYTE, desc = "位置数据类型 0：正常位置批量汇报，1：盲区补报")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 3, type = DataType.LIST, desc = "位置汇报数据项")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.total = items.size();
    }

    public static class Item {

        private int length;
        private T0200 position;

        public Item() {
        }

        public Item(int length, T0200 position) {
            this.length = length;
            this.position = position;
        }

        @Field(index = 0, type = DataType.WORD, desc = "位置汇报数据体长度")
        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        @Field(index = 2, type = DataType.OBJ, lengthName = "length", desc = "位置汇报数据体")
        public T0200 getPosition() {
            return position;
        }

        public void setPosition(T0200 position) {
            this.position = position;
        }
    }
}
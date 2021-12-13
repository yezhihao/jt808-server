package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import io.netty.buffer.ByteBufUtil;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.CAN总线数据上传)
public class T0705 extends JTMessage {

    @Field(length = 2, desc = "数据项个数(大于0)")
    private int total;
    @Field(length = 5, charset = "BCD", desc = "CAN总线数据接收时间(HHMMSSMSMS)")
    private String dateTime;
    @Field(desc = "CAN总线数据项")
    private List<Item> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.total = items.size();
    }

    public static class Item {
        @Field(length = 4, desc = "CAN ID")
        private int id;
        @Field(length = 8, desc = "CAN DATA")
        private byte[] data;

        public Item() {
        }

        public Item(int id, byte[] data) {
            this.id = id;
            this.data = data;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(128);
            sb.append("{id=").append(id);
            sb.append(",data=").append(ByteBufUtil.hexDump(data));
            sb.append('}');
            return sb.toString();
        }
    }
}
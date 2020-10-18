package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.model.Response;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.存储多媒体数据检索应答)
public class T0802 extends AbstractMessage<Header> implements Response {

    private int serialNo;
    private int total;
    private List<Item> items;

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 2, type = DataType.WORD, desc = "多媒体数据总项数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 4, type = DataType.LIST, desc = "检索项")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.total = items.size();
    }

    public static class Item {

        private int id;
        private int type;
        private int channelId;
        private int event;
        private T0200 position;

        public Item() {
        }

        public Item(int id, int type, int channelId, int event, T0200 position) {
            this.id = id;
            this.type = type;
            this.channelId = channelId;
            this.event = event;
            this.position = position;
        }

        @Field(index = 0, type = DataType.DWORD, desc = "多媒体数据ID")
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Field(index = 4, type = DataType.BYTE, desc = "多媒体类型 0：图像；1：音频；2：视频")
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Field(index = 5, type = DataType.BYTE, desc = "通道ID")
        public int getChannelId() {
            return channelId;
        }

        public void setChannelId(int channelId) {
            this.channelId = channelId;
        }

        @Field(index = 6, type = DataType.BYTE, desc = "事件项编码")
        public int getEvent() {
            return event;
        }

        public void setEvent(int event) {
            this.event = event;
        }

        @Field(index = 7, type = DataType.OBJ, length = 28, desc = "位置信息")
        public T0200 getPosition() {
            return position;
        }

        public void setPosition(T0200 position) {
            this.position = position;
        }
    }
}
package org.yzh.protocol.t808;

import io.github.yezhihao.netmc.core.model.Response;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.存储多媒体数据检索应答)
public class T0802 extends JTMessage implements Response {

    @Field(length = 2, desc = "应答流水号")
    private int responseSerialNo;
    @Field(totalUnit = 2, desc = "检索项")
    private List<Item> items;

    public int getResponseSerialNo() {
        return responseSerialNo;
    }

    public void setResponseSerialNo(int responseSerialNo) {
        this.responseSerialNo = responseSerialNo;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {
        @Field(length = 4, desc = "多媒体数据ID")
        private int id;
        @Field(length = 1, desc = "多媒体类型：0.图像 1.音频 2.视频")
        private int type;
        @Field(length = 1, desc = "通道ID")
        private int channelId;
        @Field(length = 1, desc = "事件项编码")
        private int event;
        @Field(length = 28, desc = "位置信息")
        private T0200 location;

        public Item() {
        }

        public Item(int id, int type, int channelId, int event, T0200 location) {
            this.id = id;
            this.type = type;
            this.channelId = channelId;
            this.event = event;
            this.location = location;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getChannelId() {
            return channelId;
        }

        public void setChannelId(int channelId) {
            this.channelId = channelId;
        }

        public int getEvent() {
            return event;
        }

        public void setEvent(int event) {
            this.event = event;
        }

        public T0200 getLocation() {
            return location;
        }

        public void setLocation(T0200 location) {
            this.location = location;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(768);
            sb.append("{id=").append(id);
            sb.append(",type=").append(type);
            sb.append(",channelId=").append(channelId);
            sb.append(",event=").append(event);
            sb.append(",location=").append(location);
            sb.append('}');
            return sb.toString();
        }
    }
}
package org.yzh.protocol.t808;

import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.存储多媒体数据检索应答)
public class T0802 extends AbstractMessage<Header> {

    private Integer serialNo;
    private Integer total;
    private List<Item> list;

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 2, type = DataType.WORD, desc = "多媒体数据总项数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Field(index = 4, type = DataType.LIST, desc = "检索项")
    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }

    public static class Item {

        private Integer id;
        private Integer type;
        private Integer channelId;
        private Integer event;
        private T0200 position;

        @Field(index = 0, type = DataType.DWORD, desc = "多媒体数据ID")
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Field(index = 4, type = DataType.BYTE, desc = "多媒体类型 0：图像；1：音频；2：视频")
        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        @Field(index = 5, type = DataType.BYTE, desc = "通道ID")
        public Integer getChannelId() {
            return channelId;
        }

        public void setChannelId(Integer channelId) {
            this.channelId = channelId;
        }

        @Field(index = 6, type = DataType.BYTE, desc = "事件项编码")
        public Integer getEvent() {
            return event;
        }

        public void setEvent(Integer event) {
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
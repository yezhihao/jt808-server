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
 * 该消息2019版本已删除
 */
@Message(JT808.事件设置)
public class T8301 extends JTMessage {

    /** @see org.yzh.protocol.commons.Action */
    @Field(index = 0, type = DataType.BYTE, desc = "设置类型: 0.清空 1.更新(先清空,后追加) 2.追加 3.修改 4.指定删除")
    private int type;
    @Field(index = 1, type = DataType.BYTE, desc = "设置总数")
    private int total;
    @Field(index = 2, type = DataType.LIST, desc = "事件项列表")
    private List<Event> events;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTotal() {
        if (events != null)
            return events.size();
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        this.total = events.size();
    }

    public void addEvent(int id, String content) {
        if (events == null)
            events = new ArrayList();
        events.add(new Event(id, content));
        total = events.size();
    }

    public static class Event {
        @Field(index = 0, type = DataType.BYTE, desc = "事件ID")
        private int id;
        @Field(index = 1, type = DataType.STRING, lengthSize = 1, desc = "内容")
        private String content;

        public Event() {
        }

        public Event(int id, String content) {
            this.id = id;
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 * 该消息2019版本已删除
 */
@Message(JT808.事件设置)
public class T8301 extends JTMessage {

    /** @see org.yzh.protocol.commons.Action */
    @Field(length = 1, desc = "设置类型：0.清空 1.更新(先清空,后追加) 2.追加 3.修改 4.指定删除")
    private int type;
    @Field(lengthSize = 1, desc = "事件项")
    private List<Event> events;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(int id, String content) {
        if (events == null)
            events = new ArrayList<>(2);
        events.add(new Event(id, content));
    }

    public static class Event {
        @Field(length = 1, desc = "事件ID")
        private int id;
        @Field(lengthSize = 1, desc = "内容")
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

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(40);
            sb.append("{id=").append(id);
            sb.append(",content=").append(content);
            sb.append('}');
            return sb.toString();
        }
    }
}
package org.yzh.web.jt.t808;

import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.web.config.Charsets;
import org.yzh.web.jt.basics.Header;
import org.yzh.web.jt.common.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.事件设置)
public class T8301 extends AbstractMessage<Header> {

    //清空
    public static final int Clean = 0;
    //更新（先清空，后追加）
    public static final int Update = 1;
    //追加
    public static final int Append = 2;
    //修改
    public static final int Modify = 3;
    //删除特定几项事件，之后事件项中无需带事件内容
    public static final int Delete = 4;

    private Integer type;

    private Integer total;

    private List<Event> list;

    @Field(index = 0, type = DataType.BYTE, desc = "设置类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "设置总数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Field(index = 2, type = DataType.LIST, desc = "事件项列表")
    public List<Event> getList() {
        return list;
    }

    public void setList(List<Event> list) {
        this.list = list;
    }

    public void addEvent(int id, String content) {
        if (this.list == null)
            this.list = new ArrayList();
        this.list.add(new Event(id, content));
        this.total = list.size();
    }

    public static class Event {
        private Integer id;
        private Integer length;
        private String content;

        public Event() {
        }

        public Event(Integer id, String content) {
            this.id = id;
            this.content = content;
            this.length = content.getBytes(Charsets.GBK).length;
        }

        @Field(index = 0, type = DataType.BYTE, desc = "事件ID")
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Field(index = 1, type = DataType.BYTE, desc = "长度")
        public Integer getLength() {
            if (length == null)
                this.length = content.getBytes(Charsets.GBK).length;
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        @Field(index = 2, type = DataType.STRING, lengthName = "length", desc = "内容")
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
            this.length = content.getBytes(Charsets.GBK).length;
        }
    }
}
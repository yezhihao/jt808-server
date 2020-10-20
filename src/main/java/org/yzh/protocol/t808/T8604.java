package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.mvc.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.设置多边形区域)
public class T8604 extends AbstractMessage<Header> {

    private int id;
    private int attribute;
    private String startTime;
    private String endTime;
    private int maxSpeed;
    private int duration;
    private int total;
    private List<Coordinate> items;

    @Field(index = 0, type = DataType.DWORD, desc = "区域ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Field(index = 4, type = DataType.WORD, desc = "区域属性")
    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    @Field(index = 6, type = DataType.BCD8421, length = 6, desc = "起始时间(yyMMddHHmmss)")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Field(index = 12, type = DataType.BCD8421, length = 6, desc = "结束时间(yyMMddHHmmss)")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Field(index = 18, type = DataType.WORD, desc = "最高速度")
    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Field(index = 20, type = DataType.BYTE, desc = "超速持续时间")
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Field(index = 21, type = DataType.WORD, desc = "顶点数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 23, type = DataType.LIST, desc = "顶点列表")
    public List<Coordinate> getItems() {
        return items;
    }

    public void setItems(List<Coordinate> items) {
        this.items = items;
        this.total = items.size();
    }

    public void addVertex(int latitude, int longitude) {
        if (items == null)
            items = new ArrayList();
        items.add(new Coordinate(latitude, longitude));
        total = items.size();
    }

    public static class Coordinate {
        private int latitude;
        private int longitude;

        public Coordinate() {
        }

        public Coordinate(int latitude, int longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Field(index = 0, type = DataType.DWORD, desc = "纬度")
        public int getLatitude() {
            return latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }

        @Field(index = 4, type = DataType.DWORD, desc = "经度")
        public int getLongitude() {
            return longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }
    }
}
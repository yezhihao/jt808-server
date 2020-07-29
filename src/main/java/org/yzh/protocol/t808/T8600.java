package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.设置圆形区域)
public class T8600 extends AbstractMessage<Header> {

    /** @see org.yzh.protocol.commons.ShapeAction */
    private Integer action;
    private Integer total;
    private List<Item> items;

    @Field(index = 0, type = DataType.BYTE, desc = "设置属性")
    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "区域总数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Field(index = 2, type = DataType.LIST, desc = "区域列表")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.total = items.size();
    }

    public static class Item {
        private Integer id;
        private Integer attribute;
        private Integer latitude;
        private Integer longitude;
        private Integer radius;
        private String startTime;
        private String endTime;
        private Integer maxSpeed;
        private Integer duration;

        public Item() {
        }

        public Item(Integer id, Integer attribute, Integer latitude, Integer longitude, Integer radius, String startTime, String endTime, Integer maxSpeed, Integer duration) {
            this.id = id;
            this.attribute = attribute;
            this.latitude = latitude;
            this.longitude = longitude;
            this.radius = radius;
            this.startTime = startTime;
            this.endTime = endTime;
            this.maxSpeed = maxSpeed;
            this.duration = duration;
        }

        @Field(index = 0, type = DataType.DWORD, desc = "区域ID")
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Field(index = 4, type = DataType.WORD, desc = "区域属性")
        public Integer getAttribute() {
            return attribute;
        }

        public void setAttribute(Integer attribute) {
            this.attribute = attribute;
        }

        @Field(index = 6, type = DataType.DWORD, desc = "中心点纬度")
        public Integer getLatitude() {
            return latitude;
        }

        public void setLatitude(Integer latitude) {
            this.latitude = latitude;
        }

        @Field(index = 10, type = DataType.DWORD, desc = "中心点经度")
        public Integer getLongitude() {
            return longitude;
        }

        public void setLongitude(Integer longitude) {
            this.longitude = longitude;
        }

        @Field(index = 14, type = DataType.DWORD, desc = "半径(m)")
        public Integer getRadius() {
            return radius;
        }

        public void setRadius(Integer radius) {
            this.radius = radius;
        }

        @Field(index = 18, type = DataType.BCD8421, length = 6, desc = "起始时间（yyMMddHHmmss）")
        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        @Field(index = 24, type = DataType.BCD8421, length = 6, desc = "结束时间（yyMMddHHmmss）")
        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        @Field(index = 30, type = DataType.WORD, desc = "最高速度(km/h)")
        public Integer getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(Integer maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        @Field(index = 32, type = DataType.BYTE, desc = "超速持续时间(s)")
        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }
    }
}
package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.设置路线)
public class T8606 extends AbstractMessage<Header> {

    private Integer id;
    private Integer attribute;
    private String startTime;
    private String endTime;
    private Integer total;
    private List<Point> item;

    @Field(index = 0, type = DataType.DWORD, desc = "路线ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Field(index = 4, type = DataType.WORD, desc = "路线属性")
    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    @Field(index = 6, type = DataType.BCD8421, length = 6, desc = "起始时间")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Field(index = 12, type = DataType.BCD8421, length = 6, desc = "结束时间")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Field(index = 18, type = DataType.WORD, desc = "拐点数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Field(index = 20, type = DataType.LIST, desc = "拐点列表")
    public List<Point> getItem() {
        return item;
    }

    public void setItem(List<Point> item) {
        this.item = item;
        this.total = item.size();
    }

    public void addPoint(Point point) {
        if (item == null)
            item = new ArrayList();
        item.add(point);
        total = item.size();
    }

    public void addPoint(int id) {
        if (item == null)
            item = new ArrayList();
        item.add(new Point(id));
        total = item.size();
    }

    public static class Point {
        private Integer id;
        private Integer routeId;
        private Integer latitude;
        private Integer longitude;
        private Integer width;
        private Integer attribute;
        private Integer upperLimit;
        private Integer lowerLimit;
        private Integer maxSpeed;
        private Integer duration;

        public Point() {
        }

        public Point(Integer id) {
            this.id = id;
        }

        public Point(Integer id, Integer routeId, Integer latitude, Integer longitude, Integer width, Integer attribute, Integer upperLimit, Integer lowerLimit, Integer maxSpeed, Integer duration) {
            this.id = id;
            this.routeId = routeId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.width = width;
            this.attribute = attribute;
            this.upperLimit = upperLimit;
            this.lowerLimit = lowerLimit;
            this.maxSpeed = maxSpeed;
            this.duration = duration;
        }

        @Field(index = 0, type = DataType.DWORD, desc = "拐点ID")
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Field(index = 4, type = DataType.DWORD, desc = "路段ID")
        public Integer getRouteId() {
            return routeId;
        }

        public void setRouteId(Integer routeId) {
            this.routeId = routeId;
        }

        @Field(index = 8, type = DataType.DWORD, desc = "纬度")
        public Integer getLatitude() {
            return latitude;
        }

        public void setLatitude(Integer latitude) {
            this.latitude = latitude;
        }

        @Field(index = 12, type = DataType.DWORD, desc = "经度")
        public Integer getLongitude() {
            return longitude;
        }

        public void setLongitude(Integer longitude) {
            this.longitude = longitude;
        }

        @Field(index = 16, type = DataType.BYTE, desc = "宽度")
        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        @Field(index = 17, type = DataType.WORD, desc = "属性")
        public Integer getAttribute() {
            return attribute;
        }

        public void setAttribute(Integer attribute) {
            this.attribute = attribute;
        }

        @Field(index = 18, type = DataType.WORD, desc = "路段行驶过长阈值")
        public Integer getUpperLimit() {
            return upperLimit;
        }

        public void setUpperLimit(Integer upperLimit) {
            this.upperLimit = upperLimit;
        }

        @Field(index = 20, type = DataType.WORD, desc = "路段行驶不足阈值")
        public Integer getLowerLimit() {
            return lowerLimit;
        }

        public void setLowerLimit(Integer lowerLimit) {
            this.lowerLimit = lowerLimit;
        }

        @Field(index = 22, type = DataType.WORD, desc = "路段最高速度")
        public Integer getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(Integer maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        @Field(index = 24, type = DataType.BYTE, desc = "路段超速持续时间")
        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }
    }
}
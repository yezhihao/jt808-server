package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.设置路线)
public class T8606 extends JTMessage {

    private int id;
    private int attribute;
    private String startTime;
    private String endTime;
    private int total;
    private List<Point> item;

    @Field(index = 0, type = DataType.DWORD, desc = "路线ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Field(index = 4, type = DataType.WORD, desc = "路线属性")
    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
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
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
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
        private int id;
        private int routeId;
        private int latitude;
        private int longitude;
        private int width;
        private int attribute;
        private int upperLimit;
        private int lowerLimit;
        private int maxSpeed;
        private int duration;

        public Point() {
        }

        public Point(int id) {
            this.id = id;
        }

        public Point(int id, int routeId, int latitude, int longitude, int width, int attribute, int upperLimit, int lowerLimit, int maxSpeed, int duration) {
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
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Field(index = 4, type = DataType.DWORD, desc = "路段ID")
        public int getRouteId() {
            return routeId;
        }

        public void setRouteId(int routeId) {
            this.routeId = routeId;
        }

        @Field(index = 8, type = DataType.DWORD, desc = "纬度")
        public int getLatitude() {
            return latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }

        @Field(index = 12, type = DataType.DWORD, desc = "经度")
        public int getLongitude() {
            return longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }

        @Field(index = 16, type = DataType.BYTE, desc = "宽度")
        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        @Field(index = 17, type = DataType.WORD, desc = "属性")
        public int getAttribute() {
            return attribute;
        }

        public void setAttribute(int attribute) {
            this.attribute = attribute;
        }

        @Field(index = 18, type = DataType.WORD, desc = "路段行驶过长阈值")
        public int getUpperLimit() {
            return upperLimit;
        }

        public void setUpperLimit(int upperLimit) {
            this.upperLimit = upperLimit;
        }

        @Field(index = 20, type = DataType.WORD, desc = "路段行驶不足阈值")
        public int getLowerLimit() {
            return lowerLimit;
        }

        public void setLowerLimit(int lowerLimit) {
            this.lowerLimit = lowerLimit;
        }

        @Field(index = 22, type = DataType.WORD, desc = "路段最高速度")
        public int getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(int maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        @Field(index = 24, type = DataType.BYTE, desc = "路段超速持续时间")
        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }
}
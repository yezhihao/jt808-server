package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.设置路线)
public class T8606 extends JTMessage {

    @Field(index = 0, type = DataType.DWORD, desc = "路线ID")
    private int id;
    @Field(index = 4, type = DataType.WORD, desc = "路线属性")
    private int attribute;
    @Field(index = 6, type = DataType.BCD8421, length = 6, desc = "起始时间(YYMMDDHHMMSS)")
    private String startTime;
    @Field(index = 12, type = DataType.BCD8421, length = 6, desc = "结束时间(YYMMDDHHMMSS)")
    private String endTime;
    @Field(index = 18, type = DataType.WORD, desc = "拐点数")
    private int total;
    @Field(index = 20, type = DataType.LIST, desc = "拐点列表")
    private List<Point> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Point> getItems() {
        return items;
    }

    public void setItems(List<Point> items) {
        this.items = items;
        this.total = items.size();
    }

    public static class Point {
        @Field(index = 0, type = DataType.DWORD, desc = "拐点ID")
        private int id;
        @Field(index = 4, type = DataType.DWORD, desc = "路段ID")
        private int routeId;
        @Field(index = 8, type = DataType.DWORD, desc = "纬度")
        private int latitude;
        @Field(index = 12, type = DataType.DWORD, desc = "经度")
        private int longitude;
        @Field(index = 16, type = DataType.BYTE, desc = "宽度(米)")
        private int width;
        @Field(index = 17, type = DataType.WORD, desc = "属性")
        private int attribute;
        @Field(index = 18, type = DataType.WORD, desc = "路段行驶过长阈值(秒)")
        private int upperLimit;
        @Field(index = 20, type = DataType.WORD, desc = "路段行驶不足阈值(秒)")
        private int lowerLimit;
        @Field(index = 22, type = DataType.WORD, desc = "路段最高速度(公里每小时)")
        private int maxSpeed;
        @Field(index = 24, type = DataType.BYTE, desc = "路段超速持续时间(秒)")
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRouteId() {
            return routeId;
        }

        public void setRouteId(int routeId) {
            this.routeId = routeId;
        }

        public int getLatitude() {
            return latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }

        public int getLongitude() {
            return longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getAttribute() {
            return attribute;
        }

        public void setAttribute(int attribute) {
            this.attribute = attribute;
        }

        public int getUpperLimit() {
            return upperLimit;
        }

        public void setUpperLimit(int upperLimit) {
            this.upperLimit = upperLimit;
        }

        public int getLowerLimit() {
            return lowerLimit;
        }

        public void setLowerLimit(int lowerLimit) {
            this.lowerLimit = lowerLimit;
        }

        public int getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(int maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(240);
            sb.append("{id=").append(id);
            sb.append(",routeId=").append(routeId);
            sb.append(",longitude=").append(longitude);
            sb.append(",latitude=").append(latitude);
            sb.append(",width=").append(width);
            sb.append(",attribute=").append(attribute);
            sb.append(",upperLimit=").append(upperLimit);
            sb.append(",lowerLimit=").append(lowerLimit);
            sb.append(",maxSpeed=").append(maxSpeed);
            sb.append(",duration=").append(duration);
            sb.append('}');
            return sb.toString();
        }
    }
}
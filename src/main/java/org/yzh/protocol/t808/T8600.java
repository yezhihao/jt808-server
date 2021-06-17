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
@Message(JT808.设置圆形区域)
public class T8600 extends JTMessage {

    /** @see org.yzh.protocol.commons.ShapeAction */
    private int action;
    private int total;
    private List<Item> items;

    @Field(index = 0, type = DataType.BYTE, desc = "设置属性")
    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "区域总数")
    public int getTotal() {
        if (items != null)
            return items.size();
        return total;
    }

    public void setTotal(int total) {
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
        private int id;
        private int attribute;
        private int latitude;
        private int longitude;
        private int radius;
        private String startTime;
        private String endTime;
        private int maxSpeed;
        private int duration;

        private int nightMaxSpeed;
        private String name;

        public Item() {
        }

        public Item(int id, int attribute, int latitude, int longitude, int radius, String startTime, String endTime, int maxSpeed, int duration) {
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

        public Item(int id, int attribute, int latitude, int longitude, int radius, String startTime, String endTime, int maxSpeed, int duration, int nightMaxSpeed, String name) {
            this.id = id;
            this.attribute = attribute;
            this.latitude = latitude;
            this.longitude = longitude;
            this.radius = radius;
            this.startTime = startTime;
            this.endTime = endTime;
            this.maxSpeed = maxSpeed;
            this.duration = duration;
            this.nightMaxSpeed = nightMaxSpeed;
            this.name = name;
        }

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

        @Field(index = 6, type = DataType.DWORD, desc = "中心点纬度")
        public int getLatitude() {
            return latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }

        @Field(index = 10, type = DataType.DWORD, desc = "中心点经度")
        public int getLongitude() {
            return longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }

        @Field(index = 14, type = DataType.DWORD, desc = "半径(m)")
        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        @Field(index = 18, type = DataType.BCD8421, length = 6, desc = "起始时间(YYMMDDHHMMSS)")
        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        @Field(index = 24, type = DataType.BCD8421, length = 6, desc = "结束时间(YYMMDDHHMMSS)")
        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        @Field(index = 30, type = DataType.WORD, desc = "最高速度(km/h)")
        public int getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(int maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        @Field(index = 32, type = DataType.BYTE, desc = "超速持续时间(s)")
        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        @Field(index = 33, type = DataType.WORD, desc = "夜间最高速度", version = 1)
        public int getNightMaxSpeed() {
            return nightMaxSpeed;
        }

        public void setNightMaxSpeed(int nightMaxSpeed) {
            this.nightMaxSpeed = nightMaxSpeed;
        }

        @Field(index = 35, type = DataType.STRING, lengthSize = 2, desc = "区域名称", version = 1)
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
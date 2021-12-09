package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.Bit;
import org.yzh.protocol.commons.JT808;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.设置多边形区域)
public class T8604 extends JTMessage {

    @Field(index = 0, type = DataType.DWORD, desc = "区域ID")
    private int id;
    @Field(index = 4, type = DataType.WORD, desc = "区域属性")
    private int attribute;
    @Field(index = 6, type = DataType.BCD8421, length = 6, desc = "起始时间(若区域属性0位为0则没有该字段)")
    private LocalDateTime startTime;
    @Field(index = 12, type = DataType.BCD8421, length = 6, desc = "结束时间(若区域属性0位为0则没有该字段)")
    private LocalDateTime endTime;
    @Field(index = 18, type = DataType.WORD, desc = "最高速度(公里每小时,若区域属性1位为0则没有该字段)")
    private Integer maxSpeed;
    @Field(index = 20, type = DataType.BYTE, desc = "超速持续时间(秒,若区域属性1位为0则没有该字段)")
    private Integer duration;
    @Field(index = 21, type = DataType.LIST, lengthSize = 2, desc = "顶点项")
    private List<Point> points;
    @Field(index = 23, type = DataType.WORD, desc = "夜间最高速度(公里每小时,若区域属性1位为0则没有该字段)", version = 1)
    private Integer nightMaxSpeed;
    @Field(index = 25, type = DataType.STRING, lengthSize = 2, desc = "区域名称", version = 1)
    private String name;

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.attribute = Bit.set(attribute, 0, startTime != null);
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.attribute = Bit.set(attribute, 0, endTime != null);
        this.endTime = endTime;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.attribute = Bit.set(attribute, 1, maxSpeed != null);
        this.maxSpeed = maxSpeed;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.attribute = Bit.set(attribute, 1, duration != null);
        this.duration = duration;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void addPoint(int longitude, int latitude) {
        if (points == null)
            points = new ArrayList<>(4);
        points.add(new Point(latitude, longitude));
    }

    public Integer getNightMaxSpeed() {
        return nightMaxSpeed;
    }

    public void setNightMaxSpeed(Integer nightMaxSpeed) {
        this.attribute = Bit.set(attribute, 1, nightMaxSpeed != null);
        this.nightMaxSpeed = nightMaxSpeed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Point {
        @Field(index = 0, type = DataType.DWORD, desc = "纬度")
        private int latitude;
        @Field(index = 4, type = DataType.DWORD, desc = "经度")
        private int longitude;

        public Point() {
        }

        public Point(int latitude, int longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
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

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(32);
            sb.append('{');
            sb.append("lng=").append(longitude);
            sb.append(",lat=").append(latitude);
            sb.append('}');
            return sb.toString();
        }
    }
}
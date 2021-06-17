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
 */
@Message(JT808.设置多边形区域)
public class T8604 extends JTMessage {

    private int id;
    private int attribute;
    private String startTime;
    private String endTime;
    private int maxSpeed;
    private int duration;
    private int total;
    private List<Point> points;

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

    @Field(index = 6, type = DataType.BCD8421, length = 6, desc = "起始时间(YYMMDDHHMMSS)")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Field(index = 12, type = DataType.BCD8421, length = 6, desc = "结束时间(YYMMDDHHMMSS)")
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
        if (points != null)
            return points.size();
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 23, type = DataType.LIST, desc = "顶点列表")
    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
        this.total = points.size();
    }

    public void addPoint(int longitude, int latitude) {
        if (points == null)
            points = new ArrayList();
        points.add(new Point(latitude, longitude));
        total = points.size();
    }

    public static class Point {
        private int latitude;
        private int longitude;

        public Point() {
        }

        public Point(int latitude, int longitude) {
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
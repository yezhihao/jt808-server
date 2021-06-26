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

    @Field(index = 0, type = DataType.DWORD, desc = "区域ID")
    private int id;
    @Field(index = 4, type = DataType.WORD, desc = "区域属性")
    private int attribute;
    @Field(index = 6, type = DataType.BCD8421, length = 6, desc = "起始时间(YYMMDDHHMMSS)")
    private String startTime;
    @Field(index = 12, type = DataType.BCD8421, length = 6, desc = "结束时间(YYMMDDHHMMSS)")
    private String endTime;
    @Field(index = 18, type = DataType.WORD, desc = "最高速度(公里每小时)")
    private int maxSpeed;
    @Field(index = 20, type = DataType.BYTE, desc = "超速持续时间(秒)")
    private int duration;
    @Field(index = 21, type = DataType.WORD, desc = "顶点数")
    private int total;
    @Field(index = 23, type = DataType.LIST, desc = "顶点列表")
    private List<Point> points;

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

    public int getTotal() {
        if (points != null)
            return points.size();
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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
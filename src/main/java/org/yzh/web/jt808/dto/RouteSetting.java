package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

import java.util.ArrayList;
import java.util.List;

@Type(MessageId.设置路线)
public class RouteSetting extends AbstractBody {

    private Long id;
    private Integer attribute;
    private String startTime;
    private String endTime;
    private Integer pointNumber;
    private List<Point> pointList;

    @Property(index = 0, type = DataType.DWORD, desc = "路线ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Property(index = 4, type = DataType.WORD, desc = "路线属性")
    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    @Property(index = 6, type = DataType.BCD8421, length = 6, desc = "起始时间")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Property(index = 12, type = DataType.BCD8421, length = 6, desc = "结束时间")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Property(index = 18, type = DataType.WORD, desc = "拐点数")
    public Integer getPointNumber() {
        return pointNumber;
    }

    public void setPointNumber(Integer pointNumber) {
        this.pointNumber = pointNumber;
    }

    @Property(index = 20, type = DataType.LIST, desc = "拐点列表")
    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public void addPoint(Point point) {
        if (pointList == null)
            pointList = new ArrayList();
        pointList.add(point);
        pointNumber = pointList.size();
    }

    public void addPoint(int id) {
        if (pointList == null)
            pointList = new ArrayList();
        pointList.add(new Point(id));
        pointNumber = pointList.size();
    }

    public static class Point {
        private Integer id;
        private Integer routeId;
        private Long latitude;
        private Long longitude;
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

        @Property(index = 0, type = DataType.DWORD, desc = "拐点ID")
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Property(index = 4, type = DataType.DWORD, desc = "路段ID")
        public Integer getRouteId() {
            return routeId;
        }

        public void setRouteId(Integer routeId) {
            this.routeId = routeId;
        }

        @Property(index = 8, type = DataType.DWORD, desc = "纬度")
        public Long getLatitude() {
            return latitude;
        }

        public void setLatitude(Long latitude) {
            this.latitude = latitude;
        }

        @Property(index = 12, type = DataType.DWORD, desc = "经度")
        public Long getLongitude() {
            return longitude;
        }

        public void setLongitude(Long longitude) {
            this.longitude = longitude;
        }

        @Property(index = 16, type = DataType.BYTE, desc = "宽度")
        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        @Property(index = 17, type = DataType.WORD, desc = "属性")
        public Integer getAttribute() {
            return attribute;
        }

        public void setAttribute(Integer attribute) {
            this.attribute = attribute;
        }

        @Property(index = 18, type = DataType.WORD, desc = "路段行驶过长阈值")
        public Integer getUpperLimit() {
            return upperLimit;
        }

        public void setUpperLimit(Integer upperLimit) {
            this.upperLimit = upperLimit;
        }

        @Property(index = 20, type = DataType.WORD, desc = "路段行驶不足阈值")
        public Integer getLowerLimit() {
            return lowerLimit;
        }

        public void setLowerLimit(Integer lowerLimit) {
            this.lowerLimit = lowerLimit;
        }

        @Property(index = 22, type = DataType.WORD, desc = "路段最高速度")
        public Integer getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(Integer maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        @Property(index = 24, type = DataType.BYTE, desc = "路段超速持续时间")
        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }
    }
}
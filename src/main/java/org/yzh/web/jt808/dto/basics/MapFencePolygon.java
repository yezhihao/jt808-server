package org.yzh.web.jt808.dto.basics;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;

import java.util.ArrayList;
import java.util.List;

public class MapFencePolygon extends MapFence {

    private Long id;

    private Integer attribute;

    private String startTime;

    private String endTime;

    private Integer maxSpeed;

    private Integer duration;

    private Integer vertexNumber;

    private List<Coordinate> vertexList;

    @Property(index = 0, type = DataType.DWORD, desc = "区域ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Property(index = 4, type = DataType.WORD, desc = "区域属性")
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

    @Property(index = 18, type = DataType.WORD, desc = "最高速度")
    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Property(index = 20, type = DataType.BYTE, desc = "超速持续时间")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Property(index = 21, type = DataType.WORD, desc = "顶点数")
    public Integer getVertexNumber() {
        return vertexNumber;
    }

    public void setVertexNumber(Integer vertexNumber) {
        this.vertexNumber = vertexNumber;
    }

    @Property(index = 23, type = DataType.WORD, desc = "顶点列表")
    public List<Coordinate> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<Coordinate> vertexList) {
        this.vertexList = vertexList;
    }

    public void addVertex(Long latitude, Long longitude) {
        if (vertexList == null)
            vertexList = new ArrayList();
        vertexList.add(new Coordinate(latitude, longitude));
        vertexNumber = vertexList.size();
    }

    public static class Coordinate {
        private Long latitude;
        private Long longitude;

        public Coordinate() {
        }

        public Coordinate(Long latitude, Long longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Property(index = 0, type = DataType.DWORD, desc = "纬度")
        public Long getLatitude() {
            return latitude;
        }

        public void setLatitude(Long latitude) {
            this.latitude = latitude;
        }

        @Property(index = 4, type = DataType.DWORD, desc = "经度")
        public Long getLongitude() {
            return longitude;
        }

        public void setLongitude(Long longitude) {
            this.longitude = longitude;
        }
    }
}
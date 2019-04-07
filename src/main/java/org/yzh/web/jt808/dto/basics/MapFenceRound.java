package org.yzh.web.jt808.dto.basics;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;

public class MapFenceRound extends MapFence {

    private Long id;

    private Integer attribute;

    private Long latitude;

    private Long longitude;

    private Long radius;

    private String startTime;

    private String endTime;

    private Integer maxSpeed;

    private Integer duration;

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

    @Property(index = 6, type = DataType.DWORD, desc = "中心点纬度")
    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    @Property(index = 10, type = DataType.DWORD, desc = "中心点经度")
    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    @Property(index = 14, type = DataType.DWORD, desc = "半径")
    public Long getRadius() {
        return radius;
    }

    public void setRadius(Long radius) {
        this.radius = radius;
    }

    @Property(index = 18, type = DataType.BCD8421, length = 6, desc = "起始时间")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Property(index = 24, type = DataType.BCD8421, length = 6, desc = "结束时间")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Property(index = 30, type = DataType.WORD, desc = "最高速度")
    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Property(index = 32, type = DataType.BYTE, desc = "超速持续时间")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
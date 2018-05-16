package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;

public class MapFenceRectangle extends MapFence {

    private Long id;

    private Integer attribute;

    private Long latitudeUL;

    private Long longitudeUL;

    private Long latitudeLR;

    private Long longitudeLR;

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

    @Property(index = 6, type = DataType.DWORD, desc = "左上点纬度")
    public Long getLatitudeUL() {
        return latitudeUL;
    }

    public void setLatitudeUL(Long latitudeUL) {
        this.latitudeUL = latitudeUL;
    }

    @Property(index = 10, type = DataType.DWORD, desc = "左上点经度")
    public Long getLongitudeUL() {
        return longitudeUL;
    }

    public void setLongitudeUL(Long longitudeUL) {
        this.longitudeUL = longitudeUL;
    }

    @Property(index = 14, type = DataType.DWORD, desc = "右下点经度")
    public Long getLatitudeLR() {
        return latitudeLR;
    }

    public void setLatitudeLR(Long latitudeLR) {
        this.latitudeLR = latitudeLR;
    }

    @Property(index = 18, type = DataType.DWORD, desc = "右下点经度")
    public Long getLongitudeLR() {
        return longitudeLR;
    }

    public void setLongitudeLR(Long longitudeLR) {
        this.longitudeLR = longitudeLR;
    }

    @Property(index = 22, type = DataType.BCD8421, length = 6, desc = "起始时间")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Property(index = 28, type = DataType.BCD8421, length = 6, desc = "结束时间")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Property(index = 34, type = DataType.WORD, desc = "最高速度")
    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Property(index = 36, type = DataType.BYTE, desc = "超速持续时间")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
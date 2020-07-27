package org.yzh.web.jt.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.web.jt.basics.Header;
import org.yzh.web.jt.common.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.设置多边形区域)
public class T8604 extends AbstractMessage<Header> {

    private Integer id;
    private Integer attribute;
    private String startTime;
    private String endTime;
    private Integer maxSpeed;
    private Integer duration;
    private Integer total;
    private List<Coordinate> items;

    @Field(index = 0, type = DataType.DWORD, desc = "区域ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Field(index = 4, type = DataType.WORD, desc = "区域属性")
    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
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

    @Field(index = 18, type = DataType.WORD, desc = "最高速度")
    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Field(index = 20, type = DataType.BYTE, desc = "超速持续时间")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Field(index = 21, type = DataType.WORD, desc = "顶点数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Field(index = 23, type = DataType.LIST, desc = "顶点列表")
    public List<Coordinate> getItems() {
        return items;
    }

    public void setItems(List<Coordinate> items) {
        this.items = items;
        this.total = items.size();
    }

    public void addVertex(Integer latitude, Integer longitude) {
        if (items == null)
            items = new ArrayList();
        items.add(new Coordinate(latitude, longitude));
        total = items.size();
    }

    public static class Coordinate {
        private Integer latitude;
        private Integer longitude;

        public Coordinate() {
        }

        public Coordinate(Integer latitude, Integer longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Field(index = 0, type = DataType.DWORD, desc = "纬度")
        public Integer getLatitude() {
            return latitude;
        }

        public void setLatitude(Integer latitude) {
            this.latitude = latitude;
        }

        @Field(index = 4, type = DataType.DWORD, desc = "经度")
        public Integer getLongitude() {
            return longitude;
        }

        public void setLongitude(Integer longitude) {
            this.longitude = longitude;
        }
    }
}
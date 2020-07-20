package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
@Type(MessageId.设置多边形区域)
public class T8604 extends AbstractBody {

    private Integer id;
    private Integer attribute;
    private String startTime;
    private String endTime;
    private Integer maxSpeed;
    private Integer duration;
    private Integer vertexTotal;
    private List<Coordinate> vertexes;

    @Property(index = 0, type = DataType.DWORD, desc = "区域ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    public Integer getVertexTotal() {
        return vertexTotal;
    }

    public void setVertexTotal(Integer vertexTotal) {
        this.vertexTotal = vertexTotal;
    }

    @Property(index = 23, type = DataType.LIST, desc = "顶点列表")
    public List<Coordinate> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Coordinate> vertexes) {
        this.vertexes = vertexes;
    }

    public void addVertex(Integer latitude, Integer longitude) {
        if (vertexes == null)
            vertexes = new ArrayList();
        vertexes.add(new Coordinate(latitude, longitude));
        vertexTotal = vertexes.size();
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

        @Property(index = 0, type = DataType.DWORD, desc = "纬度")
        public Integer getLatitude() {
            return latitude;
        }

        public void setLatitude(Integer latitude) {
            this.latitude = latitude;
        }

        @Property(index = 4, type = DataType.DWORD, desc = "经度")
        public Integer getLongitude() {
            return longitude;
        }

        public void setLongitude(Integer longitude) {
            this.longitude = longitude;
        }
    }
}
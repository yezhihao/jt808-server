package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.Bit;
import org.yzh.protocol.commons.JT808;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.设置路线)
public class T8606 extends JTMessage {

    @Field(length = 4, desc = "路线ID")
    private int id;
    @Field(length = 2, desc = "路线属性")
    private int attribute;
    @Field(length = 6, charset = "BCD", desc = "起始时间(若区域属性0位为0则没有该字段)")
    private LocalDateTime startTime;
    @Field(length = 6, charset = "BCD", desc = "结束时间(若区域属性0位为0则没有该字段)")
    private LocalDateTime endTime;
    @Field(totalUnit = 2, desc = "拐点列表")
    private List<Line> items;
    @Field(lengthUnit = 2, desc = "区域名称", version = 1)
    private String name;

    public void setStartTime(LocalDateTime startTime) {
        this.attribute = Bit.set(attribute, 0, startTime != null);
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.attribute = Bit.set(attribute, 0, endTime != null);
        this.endTime = endTime;
    }

    @ToString
    @Data
    @Accessors(chain = true)
    public static class Line {
        @Field(length = 4, desc = "拐点ID")
        private int id;
        @Field(length = 4, desc = "路段ID")
        private int routeId;
        @Field(length = 4, desc = "纬度")
        private int latitude;
        @Field(length = 4, desc = "经度")
        private int longitude;
        @Field(length = 1, desc = "宽度(米)")
        private int width;
        @Field(length = 1, desc = "属性")
        private int attribute;
        @Field(length = 2, desc = "路段行驶过长阈值(秒,若区域属性0位为0则没有该字段)")
        private Integer upperLimit;
        @Field(length = 2, desc = "路段行驶不足阈值(秒,若区域属性0位为0则没有该字段)")
        private Integer lowerLimit;
        @Field(length = 2, desc = "路段最高速度(公里每小时,若区域属性1位为0则没有该字段)")
        private Integer maxSpeed;
        @Field(length = 1, desc = "路段超速持续时间(秒,若区域属性1位为0则没有该字段)")
        private Integer duration;
        @Field(length = 2, desc = "夜间最高速度(公里每小时,若区域属性1位为0则没有该字段)", version = 1)
        private Integer nightMaxSpeed;

        public Line() {
        }

        public Line(int id, int routeId, int latitude, int longitude, int width, int attribute, Integer upperLimit, Integer lowerLimit, Integer maxSpeed, Integer duration) {
            this.id = id;
            this.routeId = routeId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.width = width;
            this.attribute = attribute;
            this.setUpperLimit(upperLimit);
            this.setLowerLimit(lowerLimit);
            this.setMaxSpeed(maxSpeed);
            this.setDuration(duration);
        }

        public Line(int id, int routeId, int latitude, int longitude, int width, int attribute, Integer upperLimit, Integer lowerLimit, Integer maxSpeed, Integer duration, Integer nightMaxSpeed) {
            this(id, routeId, latitude, longitude, width, attribute, upperLimit, lowerLimit, maxSpeed, duration);
            this.setNightMaxSpeed(nightMaxSpeed);
        }

        public void setUpperLimit(Integer upperLimit) {
            this.attribute = Bit.set(attribute, 0, upperLimit != null);
            this.upperLimit = upperLimit;
        }

        public void setLowerLimit(Integer lowerLimit) {
            this.attribute = Bit.set(attribute, 0, lowerLimit != null);
            this.lowerLimit = lowerLimit;
        }

        public void setMaxSpeed(Integer maxSpeed) {
            this.attribute = Bit.set(attribute, 1, maxSpeed != null);
            this.maxSpeed = maxSpeed;
        }

        public void setDuration(Integer duration) {
            this.attribute = Bit.set(attribute, 1, duration != null);
            this.duration = duration;
        }

        public void setNightMaxSpeed(Integer nightMaxSpeed) {
            this.attribute = Bit.set(attribute, 1, nightMaxSpeed != null);
            this.nightMaxSpeed = nightMaxSpeed;
        }
    }
}
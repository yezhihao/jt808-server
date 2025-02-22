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
@Message(JT808.设置矩形区域)
public class T8602 extends JTMessage {

    /** @see org.yzh.protocol.commons.ShapeAction */
    @Field(length = 1, desc = "设置属性")
    private int action;
    @Field(totalUnit = 1, desc = "区域项")
    private List<Rectangle> items;

    @ToString
    @Data
    @Accessors(chain = true)
    public static class Rectangle {
        @Field(length = 4, desc = "区域ID")
        private int id;
        @Field(length = 2, desc = "区域属性")
        private int attribute;
        @Field(length = 4, desc = "左上点纬度")
        private int latitudeUL;
        @Field(length = 4, desc = "左上点经度")
        private int longitudeUL;
        @Field(length = 4, desc = "右下点纬度")
        private int latitudeLR;
        @Field(length = 4, desc = "右下点经度")
        private int longitudeLR;
        @Field(length = 6, lengthExpression = "attrBit(0) ? 6 : 0", charset = "BCD", desc = "起始时间(若区域属性0位为0则没有该字段)")
        private LocalDateTime startTime;
        @Field(length = 6, lengthExpression = "attrBit(0) ? 6 : 0", charset = "BCD", desc = "结束时间(若区域属性0位为0则没有该字段)")
        private LocalDateTime endTime;
        @Field(length = 2, lengthExpression = "attrBit(1) ? 2 : 0", desc = "最高速度(公里每小时,若区域属性1位为0则没有该字段)")
        private Integer maxSpeed;
        @Field(length = 1, lengthExpression = "attrBit(1) ? 1 : 0", desc = "超速持续时间(秒,若区域属性1位为0则没有该字段)")
        private Integer duration;
        @Field(length = 2, lengthExpression = "attrBit(1) ? 2 : 0", desc = "夜间最高速度(公里每小时,若区域属性1位为0则没有该字段)", version = 1)
        private Integer nightMaxSpeed;
        @Field(lengthUnit = 2, desc = "区域名称", version = 1)
        private String name;

        public Rectangle() {
        }

        public Rectangle(int id, int attribute, int latitudeUL, int longitudeUL, int latitudeLR, int longitudeLR, LocalDateTime startTime, LocalDateTime endTime, Integer maxSpeed, Integer duration) {
            this.id = id;
            this.attribute = attribute;
            this.latitudeUL = latitudeUL;
            this.longitudeUL = longitudeUL;
            this.latitudeLR = latitudeLR;
            this.longitudeLR = longitudeLR;
            this.setStartTime(startTime);
            this.setEndTime(endTime);
            this.setMaxSpeed(maxSpeed);
            this.setDuration(duration);
        }

        public Rectangle(int id, int attribute, int latitudeUL, int longitudeUL, int latitudeLR, int longitudeLR, LocalDateTime startTime, LocalDateTime endTime, Integer maxSpeed, Integer duration, Integer nightMaxSpeed, String name) {
            this(id, attribute, latitudeUL, longitudeUL, latitudeLR, longitudeLR, startTime, endTime, maxSpeed, duration);
            this.setNightMaxSpeed(nightMaxSpeed);
            this.name = name;
        }

        public boolean attrBit(int i) {
            return Bit.isTrue(attribute, i);
        }

        public void setStartTime(LocalDateTime startTime) {
            this.attribute = Bit.set(attribute, 0, startTime != null);
            this.startTime = startTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.attribute = Bit.set(attribute, 0, endTime != null);
            this.endTime = endTime;
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
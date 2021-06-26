package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.设置矩形区域)
public class T8602 extends JTMessage {

    /** @see org.yzh.protocol.commons.ShapeAction */
    @Field(index = 0, type = DataType.BYTE, desc = "设置属性")
    private int action;
    @Field(index = 1, type = DataType.BYTE, desc = "区域总数")
    private int total;
    @Field(index = 2, type = DataType.LIST, desc = "区域列表")
    private List<Item> items;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.total = items.size();
    }

    public static class Item {
        @Field(index = 0, type = DataType.DWORD, desc = "区域ID")
        private int id;
        @Field(index = 4, type = DataType.WORD, desc = "区域属性")
        private int attribute;
        @Field(index = 6, type = DataType.DWORD, desc = "左上点纬度")
        private int latitudeUL;
        @Field(index = 10, type = DataType.DWORD, desc = "左上点经度")
        private int longitudeUL;
        @Field(index = 14, type = DataType.DWORD, desc = "右下点纬度")
        private int latitudeLR;
        @Field(index = 18, type = DataType.DWORD, desc = "右下点经度")
        private int longitudeLR;
        @Field(index = 22, type = DataType.BCD8421, length = 6, desc = "起始时间(YYMMDDHHMMSS)")
        private String startTime;
        @Field(index = 28, type = DataType.BCD8421, length = 6, desc = "结束时间(YYMMDDHHMMSS)")
        private String endTime;
        @Field(index = 34, type = DataType.WORD, desc = "最高速度(公里每小时)")
        private int maxSpeed;
        @Field(index = 36, type = DataType.BYTE, desc = "超速持续时间(秒)")
        private int duration;

        public Item() {
        }

        public Item(int id, int attribute, int latitudeUL, int longitudeUL, int latitudeLR, int longitudeLR, String startTime, String endTime, int maxSpeed, int duration) {
            this.id = id;
            this.attribute = attribute;
            this.latitudeUL = latitudeUL;
            this.longitudeUL = longitudeUL;
            this.latitudeLR = latitudeLR;
            this.longitudeLR = longitudeLR;
            this.startTime = startTime;
            this.endTime = endTime;
            this.maxSpeed = maxSpeed;
            this.duration = duration;
        }

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

        public int getLatitudeUL() {
            return latitudeUL;
        }

        public void setLatitudeUL(int latitudeUL) {
            this.latitudeUL = latitudeUL;
        }

        public int getLongitudeUL() {
            return longitudeUL;
        }

        public void setLongitudeUL(int longitudeUL) {
            this.longitudeUL = longitudeUL;
        }

        public int getLatitudeLR() {
            return latitudeLR;
        }

        public void setLatitudeLR(int latitudeLR) {
            this.latitudeLR = latitudeLR;
        }

        public int getLongitudeLR() {
            return longitudeLR;
        }

        public void setLongitudeLR(int longitudeLR) {
            this.longitudeLR = longitudeLR;
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

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(512);
            sb.append("{id=").append(id);
            sb.append(",attribute=").append(attribute);
            sb.append(",longitudeUL=").append(longitudeUL);
            sb.append(",latitudeUL=").append(latitudeUL);
            sb.append(",longitudeLR=").append(longitudeLR);
            sb.append(",latitudeLR=").append(latitudeLR);
            sb.append(",startTime=").append(startTime);
            sb.append(",endTime=").append(endTime);
            sb.append(",maxSpeed=").append(maxSpeed);
            sb.append(",duration=").append(duration);
            sb.append('}');
            return sb.toString();
        }
    }
}
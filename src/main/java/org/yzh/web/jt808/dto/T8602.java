package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

import java.util.List;

@Type(MessageId.设置矩形区域)
public class T8602 extends AbstractBody {

    /** 更新（先清空，后追加） */
    public static final int Update = 0;
    /** 追加 */
    public static final int Append = 1;
    /** 修改 */
    public static final int Modify = 2;

    private Integer operation;
    private Integer total;
    private List<Item> items;

    @Property(index = 0, type = DataType.BYTE, desc = "设置属性")
    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    @Property(index = 1, type = DataType.BYTE, desc = "区域总数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Property(index = 2, type = DataType.LIST, desc = "区域列表")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.total = items.size();
    }

    public static class Item {
        private Integer id;
        private Integer attribute;
        private Integer latitudeUL;
        private Integer longitudeUL;
        private Integer latitudeLR;
        private Integer longitudeLR;
        private String startTime;
        private String endTime;
        private Integer maxSpeed;
        private Integer duration;

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

        @Property(index = 6, type = DataType.DWORD, desc = "左上点纬度")
        public Integer getLatitudeUL() {
            return latitudeUL;
        }

        public void setLatitudeUL(Integer latitudeUL) {
            this.latitudeUL = latitudeUL;
        }

        @Property(index = 10, type = DataType.DWORD, desc = "左上点经度")
        public Integer getLongitudeUL() {
            return longitudeUL;
        }

        public void setLongitudeUL(Integer longitudeUL) {
            this.longitudeUL = longitudeUL;
        }

        @Property(index = 14, type = DataType.DWORD, desc = "右下点纬度")
        public Integer getLatitudeLR() {
            return latitudeLR;
        }

        public void setLatitudeLR(Integer latitudeLR) {
            this.latitudeLR = latitudeLR;
        }

        @Property(index = 18, type = DataType.DWORD, desc = "右下点经度")
        public Integer getLongitudeLR() {
            return longitudeLR;
        }

        public void setLongitudeLR(Integer longitudeLR) {
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
}
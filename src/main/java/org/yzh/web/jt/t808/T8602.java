package org.yzh.web.jt.t808;

import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.web.jt.basics.Header;
import org.yzh.web.jt.common.JT808;

import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.设置矩形区域)
public class T8602 extends AbstractMessage<Header> {

    /** 更新（先清空，后追加） */
    public static final int Update = 0;
    /** 追加 */
    public static final int Append = 1;
    /** 修改 */
    public static final int Modify = 2;

    private Integer operation;
    private Integer total;
    private List<Item> items;

    @Field(index = 0, type = DataType.BYTE, desc = "设置属性")
    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "区域总数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Field(index = 2, type = DataType.LIST, desc = "区域列表")
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

        @Field(index = 6, type = DataType.DWORD, desc = "左上点纬度")
        public Integer getLatitudeUL() {
            return latitudeUL;
        }

        public void setLatitudeUL(Integer latitudeUL) {
            this.latitudeUL = latitudeUL;
        }

        @Field(index = 10, type = DataType.DWORD, desc = "左上点经度")
        public Integer getLongitudeUL() {
            return longitudeUL;
        }

        public void setLongitudeUL(Integer longitudeUL) {
            this.longitudeUL = longitudeUL;
        }

        @Field(index = 14, type = DataType.DWORD, desc = "右下点纬度")
        public Integer getLatitudeLR() {
            return latitudeLR;
        }

        public void setLatitudeLR(Integer latitudeLR) {
            this.latitudeLR = latitudeLR;
        }

        @Field(index = 18, type = DataType.DWORD, desc = "右下点经度")
        public Integer getLongitudeLR() {
            return longitudeLR;
        }

        public void setLongitudeLR(Integer longitudeLR) {
            this.longitudeLR = longitudeLR;
        }

        @Field(index = 22, type = DataType.BCD8421, length = 6, desc = "起始时间")
        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        @Field(index = 28, type = DataType.BCD8421, length = 6, desc = "结束时间")
        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        @Field(index = 34, type = DataType.WORD, desc = "最高速度")
        public Integer getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(Integer maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        @Field(index = 36, type = DataType.BYTE, desc = "超速持续时间")
        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }
    }
}
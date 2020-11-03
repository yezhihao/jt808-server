package org.yzh.protocol.jsatl12;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;

/**
 * 报警标识号
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class AlarmId {

    private String deviceId;
    private String dateTime;
    private int order;
    private int total;
    private int reserved;

    public AlarmId() {
    }

    public AlarmId(String deviceId, String dateTime, int order, int total, int reserved) {
        this.deviceId = deviceId;
        this.dateTime = dateTime;
        this.order = order;
        this.total = total;
        this.reserved = reserved;
    }

    @Field(index = 0, type = DataType.STRING, length = 7, desc = "终端ID")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Field(index = 7, type = DataType.BCD8421, length = 6, desc = "时间")
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Field(index = 13, type = DataType.BYTE, desc = "序号")
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Field(index = 14, type = DataType.BYTE, desc = "附件数量")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 15, type = DataType.BYTE, desc = "预留")
    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AlarmId{");
        sb.append("deviceId='").append(deviceId).append('\'');
        sb.append(", dateTime='").append(dateTime).append('\'');
        sb.append(", order=").append(order);
        sb.append(", total=").append(total);
        sb.append(", reserved=").append(reserved);
        sb.append('}');
        return sb.toString();
    }
}
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
    private int serialNo;
    private int fileTotal;
    private int reserved;

    public AlarmId() {
    }

    public AlarmId(String deviceId, String dateTime, int serialNo, int fileTotal, int reserved) {
        this.deviceId = deviceId;
        this.dateTime = dateTime;
        this.serialNo = serialNo;
        this.fileTotal = fileTotal;
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
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 14, type = DataType.BYTE, desc = "附件数量")
    public int getFileTotal() {
        return fileTotal;
    }

    public void setFileTotal(int fileTotal) {
        this.fileTotal = fileTotal;
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
        sb.append(", serialNo=").append(serialNo);
        sb.append(", fileTotal=").append(fileTotal);
        sb.append(", reserved=").append(reserved);
        sb.append('}');
        return sb.toString();
    }
}
package org.yzh.web.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class DeviceInfo {

    @Schema(description = "签发日期")
    protected LocalDate issuedAt;
    @Schema(description = "预留字段")
    protected byte reserved;
    @Schema(description = "设备id")
    protected String deviceId;

    public DeviceInfo() {
    }


    public DeviceInfo(String deviceId, LocalDate issuedAt) {
        this.deviceId = deviceId;
        this.issuedAt = issuedAt;
    }

    public LocalDate getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDate issuedAt) {
        this.issuedAt = issuedAt;
    }

    public byte getReserved() {
        return reserved;
    }

    public void setReserved(byte reserved) {
        this.reserved = reserved;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceInfo{");
        sb.append("issuedAt=").append(issuedAt);
        sb.append(", reserved=").append(reserved);
        sb.append(", deviceId=").append(deviceId);
        sb.append('}');
        return sb.toString();
    }
}
package org.yzh.web.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DeviceInfo {

    /** token版本 */
    @JsonProperty("ve")
    private byte version;
    /** 签发时间（秒） */
    @JsonProperty("ia")
    private int issuedAt;
    /** 有效期 （秒） */
    @JsonProperty("va")
    private int validAt;
    /** 车牌颜色 */
    @JsonProperty("pc")
    private byte plateColor;
    /** 车牌号 */
    @JsonProperty("pn")
    private String plateNo;
    /** 设备ID */
    @JsonProperty("di")
    private String deviceId;

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public int getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(int issuedAt) {
        this.issuedAt = issuedAt;
    }

    public int getValidAt() {
        return validAt;
    }

    public void setValidAt(int validAt) {
        this.validAt = validAt;
    }

    public byte getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(byte plateColor) {
        this.plateColor = plateColor;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("version", version)
                .append("issuedAt", issuedAt)
                .append("validAt", validAt)
                .append("plateColor", plateColor)
                .append("plateNo", plateNo)
                .append("deviceId", deviceId)
                .toString();
    }
}
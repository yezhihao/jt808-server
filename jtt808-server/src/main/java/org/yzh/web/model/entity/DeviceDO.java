package org.yzh.web.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import org.yzh.protocol.t808.T0200;

import java.util.Objects;

public class DeviceDO {

    @Schema(description = "设备id")
    private String deviceId;
    @Schema(description = "设备手机号")
    private String mobileNo;
    @Schema(description = "车牌号")
    private String plateNo;
    @Schema(description = "机构id")
    protected int agencyId;
    @Schema(description = "司机id")
    protected int driverId;
    @Schema(description = "协议版本号")
    private int protocolVersion;
    @Schema(description = "实时状态")
    private T0200 location;

    public DeviceDO() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public T0200 getLocation() {
        return location;
    }

    public void setLocation(T0200 location) {
        this.location = location;
    }

    public DeviceDO mobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DeviceDO other = (DeviceDO) that;
        return Objects.equals(this.deviceId, other.deviceId);
    }

    @Override
    public int hashCode() {
        return ((deviceId == null) ? 0 : deviceId.hashCode());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(256);
        sb.append("DeviceDO{deviceId=").append(deviceId);
        sb.append(", mobileNo=").append(mobileNo);
        sb.append(", plateNo=").append(plateNo);
        sb.append(", protocolVersion=").append(protocolVersion);
        sb.append('}');
        return sb.toString();
    }
}
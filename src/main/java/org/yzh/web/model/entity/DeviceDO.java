package org.yzh.web.model.entity;

import java.time.LocalDateTime;

public class DeviceDO {

    private String deviceId;
    private Integer agencyId;
    private Integer vehicleId;
    private String mobileNo;
    private String iccid;
    private String imei;
    private LocalDateTime registerTime;
    private LocalDateTime installTime;
    private Integer protocolVersion;
    private String softwareVersion;
    private String firmwareVersion;
    private String hardwareVersion;
    private String deviceModel;
    private String makerId;
    private Boolean deleted;
    private String updatedBy;
    private String createdBy;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public LocalDateTime getInstallTime() {
        return installTime;
    }

    public void setInstallTime(LocalDateTime installTime) {
        this.installTime = installTime;
    }

    public Integer getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(Integer protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getMakerId() {
        return makerId;
    }

    public void setMakerId(String makerId) {
        this.makerId = makerId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DeviceDO deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public DeviceDO agencyId(Integer agencyId) {
        this.agencyId = agencyId;
        return this;
    }

    public DeviceDO vehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public DeviceDO mobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public DeviceDO iccid(String iccid) {
        this.iccid = iccid;
        return this;
    }

    public DeviceDO imei(String imei) {
        this.imei = imei;
        return this;
    }

    public DeviceDO registerTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
        return this;
    }

    public DeviceDO installTime(LocalDateTime installTime) {
        this.installTime = installTime;
        return this;
    }

    public DeviceDO protocolVersion(Integer protocolVersion) {
        this.protocolVersion = protocolVersion;
        return this;
    }

    public DeviceDO softwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
        return this;
    }

    public DeviceDO firmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
        return this;
    }

    public DeviceDO hardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
        return this;
    }

    public DeviceDO deviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
        return this;
    }

    public DeviceDO makerId(String makerId) {
        this.makerId = makerId;
        return this;
    }

    public DeviceDO deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public DeviceDO updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public DeviceDO createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public DeviceDO updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public DeviceDO createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
        return (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()));
    }

    @Override
    public int hashCode() {
        return ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
    }
}
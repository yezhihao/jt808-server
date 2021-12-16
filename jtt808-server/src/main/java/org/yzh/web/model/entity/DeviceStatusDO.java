package org.yzh.web.model.entity;

import java.time.LocalDateTime;

public class DeviceStatusDO {

    private String deviceId;
    private Boolean online;
    private Integer agencyId;
    private String plateNo;
    private LocalDateTime deviceTime;
    private String mobileNo;
    private Integer vehicleId;
    private Integer warnBit;
    private Integer statusBit;
    private Integer longitude;
    private Integer latitude;
    private Integer altitude;
    private Integer speed;
    private Integer direction;
    private String address;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public LocalDateTime getDeviceTime() {
        return deviceTime;
    }

    public void setDeviceTime(LocalDateTime deviceTime) {
        this.deviceTime = deviceTime;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getWarnBit() {
        return warnBit;
    }

    public void setWarnBit(Integer warnBit) {
        this.warnBit = warnBit;
    }

    public Integer getStatusBit() {
        return statusBit;
    }

    public void setStatusBit(Integer statusBit) {
        this.statusBit = statusBit;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public DeviceStatusDO deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public DeviceStatusDO online(Boolean online) {
        this.online = online;
        return this;
    }

    public DeviceStatusDO agencyId(Integer agencyId) {
        this.agencyId = agencyId;
        return this;
    }

    public DeviceStatusDO plateNo(String plateNo) {
        this.plateNo = plateNo;
        return this;
    }

    public DeviceStatusDO deviceTime(LocalDateTime deviceTime) {
        this.deviceTime = deviceTime;
        return this;
    }

    public DeviceStatusDO mobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public DeviceStatusDO vehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public DeviceStatusDO warnBit(Integer warnBit) {
        this.warnBit = warnBit;
        return this;
    }

    public DeviceStatusDO statusBit(Integer statusBit) {
        this.statusBit = statusBit;
        return this;
    }

    public DeviceStatusDO longitude(Integer longitude) {
        this.longitude = longitude;
        return this;
    }

    public DeviceStatusDO latitude(Integer latitude) {
        this.latitude = latitude;
        return this;
    }

    public DeviceStatusDO altitude(Integer altitude) {
        this.altitude = altitude;
        return this;
    }

    public DeviceStatusDO speed(Integer speed) {
        this.speed = speed;
        return this;
    }

    public DeviceStatusDO direction(Integer direction) {
        this.direction = direction;
        return this;
    }

    public DeviceStatusDO address(String address) {
        this.address = address;
        return this;
    }

    public DeviceStatusDO updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public DeviceStatusDO createdAt(LocalDateTime createdAt) {
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
        DeviceStatusDO other = (DeviceStatusDO) that;
        return (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
                && (this.getOnline() == null ? other.getOnline() == null : this.getOnline().equals(other.getOnline()))
                && (this.getAgencyId() == null ? other.getAgencyId() == null : this.getAgencyId().equals(other.getAgencyId()))
                && (this.getPlateNo() == null ? other.getPlateNo() == null : this.getPlateNo().equals(other.getPlateNo()))
                && (this.getDeviceTime() == null ? other.getDeviceTime() == null : this.getDeviceTime().equals(other.getDeviceTime()))
                && (this.getMobileNo() == null ? other.getMobileNo() == null : this.getMobileNo().equals(other.getMobileNo()))
                && (this.getVehicleId() == null ? other.getVehicleId() == null : this.getVehicleId().equals(other.getVehicleId()))
                && (this.getWarnBit() == null ? other.getWarnBit() == null : this.getWarnBit().equals(other.getWarnBit()))
                && (this.getStatusBit() == null ? other.getStatusBit() == null : this.getStatusBit().equals(other.getStatusBit()))
                && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
                && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()))
                && (this.getAltitude() == null ? other.getAltitude() == null : this.getAltitude().equals(other.getAltitude()))
                && (this.getSpeed() == null ? other.getSpeed() == null : this.getSpeed().equals(other.getSpeed()))
                && (this.getDirection() == null ? other.getDirection() == null : this.getDirection().equals(other.getDirection()))
                && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
                && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()))
                && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getOnline() == null) ? 0 : getOnline().hashCode());
        result = prime * result + ((getAgencyId() == null) ? 0 : getAgencyId().hashCode());
        result = prime * result + ((getPlateNo() == null) ? 0 : getPlateNo().hashCode());
        result = prime * result + ((getDeviceTime() == null) ? 0 : getDeviceTime().hashCode());
        result = prime * result + ((getMobileNo() == null) ? 0 : getMobileNo().hashCode());
        result = prime * result + ((getVehicleId() == null) ? 0 : getVehicleId().hashCode());
        result = prime * result + ((getWarnBit() == null) ? 0 : getWarnBit().hashCode());
        result = prime * result + ((getStatusBit() == null) ? 0 : getStatusBit().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getAltitude() == null) ? 0 : getAltitude().hashCode());
        result = prime * result + ((getSpeed() == null) ? 0 : getSpeed().hashCode());
        result = prime * result + ((getDirection() == null) ? 0 : getDirection().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("DeviceStatus[");
        sb.append("deviceId=").append(deviceId);
        sb.append(", online=").append(online);
        sb.append(", agencyId=").append(agencyId);
        sb.append(", plateNo=").append(plateNo);
        sb.append(", deviceTime=").append(deviceTime);
        sb.append(", mobileNo=").append(mobileNo);
        sb.append(", vehicleId=").append(vehicleId);
        sb.append(", warnBit=").append(warnBit);
        sb.append(", statusBit=").append(statusBit);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", altitude=").append(altitude);
        sb.append(", speed=").append(speed);
        sb.append(", direction=").append(direction);
        sb.append(", address=").append(address);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", createdAt=").append(createdAt);
        sb.append(']');
        return sb.toString();
    }
}
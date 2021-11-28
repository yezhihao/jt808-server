package org.yzh.web.model.entity;

import java.time.LocalDateTime;

public class VehicleDO {

    private Integer id;
    private Integer agencyId;
    private String deviceId;
    private String plateNo;
    private String vinNo;
    private String engineNo;
    private Integer plateColor;
    private Integer cityId;
    private Integer provinceId;
    private Integer vehicleType;
    private String remark;
    private String updatedBy;
    private String createdBy;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private LocalDateTime areaUpdatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getVinNo() {
        return vinNo;
    }

    public void setVinNo(String vinNo) {
        this.vinNo = vinNo;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public Integer getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(Integer plateColor) {
        this.plateColor = plateColor;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public LocalDateTime getAreaUpdatedAt() {
        return areaUpdatedAt;
    }

    public void setAreaUpdatedAt(LocalDateTime areaUpdatedAt) {
        this.areaUpdatedAt = areaUpdatedAt;
    }

    public VehicleDO id(Integer id) {
        this.id = id;
        return this;
    }

    public VehicleDO agencyId(Integer agencyId) {
        this.agencyId = agencyId;
        return this;
    }

    public VehicleDO deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public VehicleDO plateNo(String plateNo) {
        this.plateNo = plateNo;
        return this;
    }

    public VehicleDO vinNo(String vinNo) {
        this.vinNo = vinNo;
        return this;
    }

    public VehicleDO engineNo(String engineNo) {
        this.engineNo = engineNo;
        return this;
    }

    public VehicleDO plateColor(Integer plateColor) {
        this.plateColor = plateColor;
        return this;
    }

    public VehicleDO cityId(Integer cityId) {
        this.cityId = cityId;
        return this;
    }

    public VehicleDO provinceId(Integer provinceId) {
        this.provinceId = provinceId;
        return this;
    }

    public VehicleDO vehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public VehicleDO remark(String remark) {
        this.remark = remark;
        return this;
    }

    public VehicleDO updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public VehicleDO createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public VehicleDO updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public VehicleDO createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public VehicleDO areaUpdatedAt(LocalDateTime areaUpdatedAt) {
        this.areaUpdatedAt = areaUpdatedAt;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VehicleDO{");
        sb.append("id=").append(id);
        sb.append(", agencyId=").append(agencyId);
        sb.append(", deviceId='").append(deviceId).append('\'');
        sb.append(", plateNo='").append(plateNo).append('\'');
        sb.append(", vinNo='").append(vinNo).append('\'');
        sb.append(", engineNo='").append(engineNo).append('\'');
        sb.append(", plateColor=").append(plateColor);
        sb.append(", cityId=").append(cityId);
        sb.append(", provinceId=").append(provinceId);
        sb.append(", vehicleType=").append(vehicleType);
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", updatedBy='").append(updatedBy).append('\'');
        sb.append(", createdBy='").append(createdBy).append('\'');
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", areaUpdatedAt=").append(areaUpdatedAt);
        sb.append('}');
        return sb.toString();
    }
}
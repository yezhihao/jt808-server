package org.yzh.component.area.model.entity;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 区域实体类
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class AreaDO {

    @Schema(description = "区域id")
    private Integer id;
    @Schema(description = "机构id")
    private Integer agencyId;
    @Schema(description = "名称")
    private String name;
    @Schema(description = "描述")
    private String areaDesc;
    @Schema(description = "几何类型: 1.圆形 2.矩形 3.多边形 4.路线")
    private Integer geomType;
    @Schema(description = "几何数据: 圆形[x，y，r] 矩形[x，y，x，y] 多边形[x，y，x，y，x，y] 路线[x，y，x，y，w]")
    private String geomText;
    @Schema(description = "标记类型: 1.作业区 2.停车场 3.禁行区")
    private Integer markType;
    @Schema(description = "限制出入: 0.无 1.进区域 2.出区域")
    private Integer limitInOut;
    @Schema(description = "限速(公里每小时)")
    private Integer limitSpeed;
    @Schema(description = "限停(分钟)")
    private Integer limitTime;
    @Schema(description = "生效日(按位,周一至周日)")
    private Integer weeks;
    @Schema(description = "开始日期")
    private LocalDate startDate;
    @Schema(description = "结束日期")
    private LocalDate endDate;
    @Schema(description = "开始时间")
    private LocalTime startTime;
    @Schema(description = "结束时间")
    private LocalTime endTime;
    @Schema(description = "删除标志")
    private Boolean deleted;
    @Schema(description = "更新者")
    private String updatedBy;
    @Schema(description = "创建者")
    private String createdBy;
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    public AreaDO() {
    }

    public AreaDO(int id) {
        this.id = id;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaDesc() {
        return areaDesc;
    }

    public void setAreaDesc(String areaDesc) {
        this.areaDesc = areaDesc;
    }

    public Integer getGeomType() {
        return geomType;
    }

    public void setGeomType(Integer geomType) {
        this.geomType = geomType;
    }

    public String getGeomText() {
        return geomText;
    }

    public void setGeomText(String geomText) {
        this.geomText = geomText;
    }

    public Integer getMarkType() {
        return markType;
    }

    public void setMarkType(Integer markType) {
        this.markType = markType;
    }

    public Integer getLimitInOut() {
        return limitInOut;
    }

    public void setLimitInOut(Integer limitInOut) {
        this.limitInOut = limitInOut;
    }

    public Integer getLimitSpeed() {
        return limitSpeed;
    }

    public void setLimitSpeed(Integer limitSpeed) {
        this.limitSpeed = limitSpeed;
    }

    public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }

    public Integer getWeeks() {
        return weeks;
    }

    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Boolean isDeleted() {
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

    public AreaDO id(Integer id) {
        this.id = id;
        return this;
    }

    public AreaDO agencyId(Integer agencyId) {
        this.agencyId = agencyId;
        return this;
    }

    public AreaDO name(String name) {
        this.name = name;
        return this;
    }

    public AreaDO areaDesc(String areaDesc) {
        this.areaDesc = areaDesc;
        return this;
    }

    public AreaDO geomType(Integer geomType) {
        this.geomType = geomType;
        return this;
    }

    public AreaDO geomText(String geomText) {
        this.geomText = geomText;
        return this;
    }

    public AreaDO markType(Integer markType) {
        this.markType = markType;
        return this;
    }

    public AreaDO limitInOut(Integer limitInOut) {
        this.limitInOut = limitInOut;
        return this;
    }

    public AreaDO limitSpeed(Integer limitSpeed) {
        this.limitSpeed = limitSpeed;
        return this;
    }

    public AreaDO limitTime(Integer limitTime) {
        this.limitTime = limitTime;
        return this;
    }

    public AreaDO weeks(Integer weeks) {
        this.weeks = weeks;
        return this;
    }

    public AreaDO startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public AreaDO endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public AreaDO startTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public AreaDO endTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public AreaDO deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public AreaDO updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public AreaDO createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public AreaDO updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public AreaDO createdAt(LocalDateTime createdAt) {
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
        AreaDO other = (AreaDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final Integer prime = 31;
        Integer result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AreaDO{");
        sb.append("id=").append(id);
        sb.append(", agencyId=").append(agencyId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", geomType=").append(geomType);
        sb.append(", markType=").append(markType);
        sb.append(", limitInOut=").append(limitInOut);
        sb.append(", limitSpeed=").append(limitSpeed);
        sb.append(", limitTime=").append(limitTime);
        sb.append(", weeks=[").append(weeks).append(']');
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", deleted=").append(deleted);
        sb.append(", areaDesc='").append(areaDesc).append('\'');
        sb.append(", geomText='").append(geomText).append('\'');
        sb.append(", updatedBy='").append(updatedBy).append('\'');
        sb.append(", createdBy='").append(createdBy).append('\'');
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
package org.yzh.component.area.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class AreaQuery {

    @Schema(description = "区域id")
    private Integer id;
    @Schema(description = "机构id")
    private Integer agencyId;
    @Schema(description = "名称")
    private String name;
    @Schema(description = "几何类型: 1.圆形 2.矩形 3.多边形 4.路线")
    private Integer geomType;
    @Schema(description = "标记类型: 1.作业区 2.停车场 3.禁行区")
    private Integer markType;
    @Schema(description = "限制出入: 0.无 1.进区域 2.出区域")
    private Integer limitInOut;
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
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

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

    public Integer getGeomType() {
        return geomType;
    }

    public void setGeomType(Integer geomType) {
        this.geomType = geomType;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AreaQuery id(Integer id) {
        this.id = id;
        return this;
    }

    public AreaQuery agencyId(Integer agencyId) {
        this.agencyId = agencyId;
        return this;
    }

    public AreaQuery name(String name) {
        this.name = name;
        return this;
    }

    public AreaQuery geomType(Integer geomType) {
        this.geomType = geomType;
        return this;
    }

    public AreaQuery markType(Integer markType) {
        this.markType = markType;
        return this;
    }

    public AreaQuery limitInOut(Integer limitInOut) {
        this.limitInOut = limitInOut;
        return this;
    }

    public AreaQuery startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public AreaQuery endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public AreaQuery startTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public AreaQuery endTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public AreaQuery deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public AreaQuery updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
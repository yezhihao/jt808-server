package org.yzh.component.area.model;

import org.yzh.component.area.GeometryFactory;
import org.yzh.component.area.model.domain.DateTimeRange;
import org.yzh.component.area.model.domain.Geometry;
import org.yzh.component.area.model.entity.AreaDO;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Area implements Geometry, DateTimeRange {

    private int id;             //区域id
    private int agencyId;       //机构id
    private String name;        //名称
    private String areaDesc;    //描述
    private int geomType;       //几何类型: 1.圆形 2.矩形 3.多边形 4.路线
    private int markType;       //标记类型: 1.作业区 2.停车场 3.禁行区
    private int limitInOut;     //限制出入: 0.无 1.进区域 2.出区域
    private int limitSpeed;     //限速(公里每小时)
    private int limitTime;      //限停(分钟)
    private int weeks;          //生效日(按位,周一至周日)
    private LocalDate startDate;//开始日期
    private LocalDate endDate;  //结束日期
    private LocalTime startTime;//开始时间
    private LocalTime endTime;  //结束时间
    private Geometry geometry;  //区域几何图形

    private Area() {
    }

    public static Area build(GeometryFactory factory, AreaDO record) {
        Area area = new Area();
        area.id = record.getId();
        area.agencyId = record.getAgencyId();
        area.name = record.getName();
        area.areaDesc = record.getAreaDesc();
        area.geomType = record.getGeomType();
        area.markType = record.getMarkType();
        area.limitInOut = record.getLimitInOut();
        area.limitSpeed = record.getLimitSpeed();
        area.limitTime = record.getLimitTime();
        area.weeks = record.getWeeks();
        area.startDate = record.getStartDate();
        area.endDate = record.getEndDate();
        area.startTime = record.getStartTime();
        area.endTime = record.getEndTime();
        area.geometry = factory.getInstance(record.getGeomType(), record.getGeomText());
        return area;
    }

    @Override
    public boolean contains(double x, double y) {
        return geometry.contains(x, y);
    }

    public int getId() {
        return id;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public String getName() {
        return name;
    }

    public String getAreaDesc() {
        return areaDesc;
    }

    public int getGeomType() {
        return geomType;
    }

    public int getMarkType() {
        return markType;
    }

    public int getLimitInOut() {
        return limitInOut;
    }

    public int getLimitSpeed() {
        return limitSpeed;
    }

    public int getLimitTime() {
        return limitTime;
    }

    @Override
    public int getWeeks() {
        return weeks;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public LocalTime getStartTime() {
        return startTime;
    }

    @Override
    public LocalTime getEndTime() {
        return endTime;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
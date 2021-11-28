package org.yzh.component.area.model;

import org.yzh.component.area.GeometryFactory;
import org.yzh.component.area.model.domain.DateTimeRange;
import org.yzh.component.area.model.domain.Geometry;
import org.yzh.component.area.model.entity.AreaDO;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Area extends DateTimeRange implements Geometry {

    public final int id;             // 区域id
    public final int agencyId;       // 机构id
    public final String name;        // 名称
    public final String areaDesc;    // 描述
    public final int geomType;       // 几何类型: 1.圆形 2.矩形 3.多边形 4.路线
    public final int markType;       // 标记类型: 1.作业区 2.停车场 3.禁行区
    public final int limitInOut;     // 限制出入: 0.无 1.进区域 2.出区域
    public final int limitSpeed;     // 限速(1/10公里每小时)
    public final int limitTime;      // 限停(秒)
    public final Geometry geometry;  // 区域几何图形

    private Area(AreaDO record, Geometry geometry) {
        super(record.getStartTime(), record.getEndTime(), record.getStartDate(), record.getEndDate(), record.getWeeks());
        this.id = record.getId();
        this.agencyId = record.getAgencyId();
        this.name = record.getName();
        this.areaDesc = record.getAreaDesc();
        this.geomType = record.getGeomType();
        this.markType = record.getMarkType();
        this.limitInOut = record.getLimitInOut();
        this.limitSpeed = record.getLimitSpeed() <= 0 ? Integer.MAX_VALUE : record.getLimitSpeed() * 10;
        this.limitTime = record.getLimitTime() * 60;
        this.geometry = geometry;
    }

    public static Area build(AreaDO record, GeometryFactory factory) {
        return new Area(record, factory.getInstance(record.getGeomType(), record.getGeomText()));
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

    public Geometry getGeometry() {
        return geometry;
    }
}
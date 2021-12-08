package org.yzh.web.model.vo;

import io.github.yezhihao.netmc.session.Session;
import io.swagger.v3.oas.annotations.media.Schema;
import org.yzh.commons.mybatis.PageInfo;
import org.yzh.commons.util.CoordType;
import org.yzh.commons.util.StrUtils;
import org.yzh.component.area.GeometryFactory;
import org.yzh.component.area.model.domain.Geometry;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.protocol.T0200;

import java.util.function.Predicate;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class DeviceQuery extends PageInfo implements Predicate<Session> {

    @Schema(description = "机构ID")
    protected Integer agencyId;
    @Schema(description = "车辆ID")
    protected Integer vehicleId;

    @Schema(description = "设备ID")
    protected String deviceId;
    @Schema(description = "终端ID")
    protected String clientId;
    @Schema(description = "车牌号")
    protected String plateNo;

    @Schema(description = "几何数据: 圆形[x,y,r] 矩形[x,y,x,y] 多边形[x,y,x,y,x,y] 路线[x,y,x,y,w]")
    protected String areaRaw;
    @Schema(description = "几何类型: 1.圆形 2.矩形 3.多边形 4.路线")
    protected int areaType = 1;
    @Schema(description = "坐标类型: wgs84 、gcj02、bd09")
    protected CoordType coordType = CoordType.wgs84;

    @Schema(hidden = true)
    private Geometry geometry;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public CoordType getCoordType() {
        return coordType;
    }

    public void setCoordType(CoordType coordType) {
        this.coordType = coordType;
    }

    public int getAreaType() {
        return areaType;
    }

    public void setAreaType(int areaType) {
        this.areaType = areaType;
    }

    public String getAreaRaw() {
        return areaRaw;
    }

    public void setAreaRaw(String areaRaw) {
        this.areaRaw = areaRaw;
    }

    public boolean isEmpty() {
        if (agencyId != null)
            return false;
        if (vehicleId != null)
            return false;

        if (StrUtils.isNotBlank(deviceId))
            return false;
        if (StrUtils.isNotBlank(clientId))
            return false;
        if (StrUtils.isNotBlank(plateNo))
            return false;

        if (StrUtils.isNotBlank(areaRaw)) {
            if (geometry == null)
                geometry = GeometryFactory.build(coordType.WGS84, areaType, areaRaw);
            return false;
        }
        return true;
    }

    @Override
    public boolean test(Session session) {
        DeviceInfo that = SessionKey.getDeviceInfo(session);
        T0200 snapshot = SessionKey.getSnapshot(session);

        boolean result = true;

        if (snapshot != null) {
            if (geometry != null)
                result &= geometry.contains(snapshot.getLng(), snapshot.getLat());
        }

        if (that != null) {
            if (this.agencyId != null)
                result &= that.agencyId == this.agencyId;
            if (this.vehicleId != null)
                result &= that.vehicleId == this.vehicleId;
            if (this.plateNo != null && that.plateNo != null)
                result &= that.plateNo.startsWith(this.plateNo);
            if (this.deviceId != null && that.deviceId != null)
                result &= that.deviceId.startsWith(this.deviceId);
            if (this.clientId != null && that.clientId != null)
                result &= that.clientId.startsWith(this.clientId);
        }
        return result;
    }
}
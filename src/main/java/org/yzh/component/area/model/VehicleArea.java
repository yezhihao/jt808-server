package org.yzh.component.area.model;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class VehicleArea {

    private final int vehicleId;
    private final int areaId;
    private final TArea area;
    protected long entryTime;

    public VehicleArea(int vehicleId, int areaId, TArea area) {
        this.vehicleId = vehicleId;
        this.areaId = areaId;
        this.area = area;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public int getAreaId() {
        return areaId;
    }

    public Area getArea() {
        return area.delegate();
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }
}

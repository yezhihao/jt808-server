package org.yzh.component.area.model;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class VehicleArea {

    private final TArea area;
    private long entryTime;

    public VehicleArea(TArea area) {
        this.area = area;
    }

    public Area getArea() {
        return area.get();
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }
}
package org.yzh.protocol.commons.transform.attribute;

import org.yzh.protocol.t808.T0200;

import java.time.LocalDateTime;

public abstract class Alarm {

    private T0200 location;

    private String platformAlarmId;

    public T0200 getLocation() {
        return location;
    }

    public void setLocation(T0200 location) {
        this.location = location;
    }

    public String getPlatformAlarmId() {
        return platformAlarmId;
    }

    public void setPlatformAlarmId(String platformAlarmId) {
        this.platformAlarmId = platformAlarmId;
    }

    static int buildType(int key, int type) {
        return (key * 100) + type;
    }

    //报警来源：0.设备报警 1.苏标报警 127.平台报警
    public int getSource() {
        return 0;
    }

    public int getAreaId() {
        return 0;
    }

    public int getState() {
        return 1;
    }

    //上报时间
    public LocalDateTime getDateTime() {
        if (location == null)
            return null;
        return location.getDeviceTime();
    }

    //报警时间
    public LocalDateTime getAlarmTime() {
        if (location == null)
            return null;
        return location.getDeviceTime();
    }

    //报警类别<=255
    public int getCategory() {
        return 0;
    }

    //报警类型
    public abstract int getAlarmType();

    //报警级别
    public int getLevel() {
        return 0;
    }

    //gps经度
    public int getLongitude() {
        if (location == null)
            return 0;
        return location.getLongitude();
    }

    //gps纬度
    public int getLatitude() {
        if (location == null)
            return 0;
        return location.getLatitude();
    }

    //海拔(米)
    public int getAltitude() {
        if (location == null)
            return 0;
        return location.getAltitude();
    }

    //速度(公里每小时)
    public int getSpeed() {
        if (location == null)
            return 0;
        return location.getSpeed() / 10;
    }

    //车辆状态
    public int getStatusBit() {
        if (location == null)
            return 0;
        return location.getStatusBit();
    }

    //序号(同一时间点报警的序号，从0循环累加)
    public int getSequenceNo() {
        return 0;
    }

    //附件总数
    public int getFileTotal() {
        return 0;
    }

    //扩展信息
    public String getExtra() {
        return null;
    }
}
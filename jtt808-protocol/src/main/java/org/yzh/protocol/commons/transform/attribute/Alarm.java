package org.yzh.protocol.commons.transform.attribute;

import java.time.LocalDateTime;

public abstract class Alarm {

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

    public abstract LocalDateTime getAlarmTime();//报警时间

    public abstract String getDeviceId();//设备id

    public abstract int getCategory();//报警类别

    public abstract int getAlarmType();//报警类型

    public abstract int getLevel();//报警级别

    public abstract int getLongitude();//gps经度

    public abstract int getLatitude();//gps纬度

    public abstract int getAltitude();//海拔(米)

    public abstract int getSpeed();//速度(公里每小时)

    public abstract int getStatusBit();//车辆状态

    public abstract int getSequenceNo();//序号(同一时间点报警的序号，从0循环累加)

    public abstract int getFileTotal();//附件总数

    public abstract String getExtra();//扩展信息
}
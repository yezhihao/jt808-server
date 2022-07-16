package org.yzh.protocol.commons.transform.attribute;

import java.time.LocalDateTime;

public interface Alarm {

    static int buildType(int key, int type) {
        return (key * 100) + type;
    }

    LocalDateTime getAlarmTime();//报警时间

    //报警来源：0.设备报警 127.平台报警
    default int getSource() {
        return 0;
    }

    int getCategory();//报警类别

    int getAlarmType();//报警类型

    int getLevel();//报警级别

    int getLongitude();//gps经度

    int getLatitude();//gps纬度

    int getAltitude();//海拔(米)

    int getSpeed();//速度(公里每小时)

    int getStatusBit();//车辆状态

    int getSerialNo();//序号(同一时间点报警的序号，从0循环累加)

    int getFileTotal();//附件总数

    String getExtra();//扩展信息
}
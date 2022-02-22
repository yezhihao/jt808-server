package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.annotation.Field;
import org.yzh.protocol.jsatl12.AlarmId;

import java.time.LocalDateTime;

/**
 * 盲点监测 0x67
 */
public class AlarmBSD implements Alarm {

    public static final int key = 103;

    @Field(length = 4, desc = "报警ID")
    private long id;
    @Field(length = 1, desc = "标志状态：0.不可用 1.开始标志 2.结束标志")
    private int state;
    @Field(length = 1, desc = "报警/事件类型：1.后方接近报警 2.左侧后方接近报警 3.右侧后方接近报警")
    private int type;
    @Field(length = 1, desc = "车速")
    private int speed;
    @Field(length = 2, desc = "高程")
    private int altitude;
    @Field(length = 4, desc = "纬度")
    private int latitude;
    @Field(length = 4, desc = "经度")
    private int longitude;
    @Field(length = 6, charset = "BCD", desc = "日期时间")
    private LocalDateTime dateTime;
    @Field(length = 2, desc = "车辆状态")
    private int statusBit;
    @Field(length = 16, desc = "报警标识号", version = {-1, 0})
    @Field(length = 40, desc = "报警标识号(粤标)", version = 1)
    private AlarmId alarmId;

    @Override
    public int getCategory() {
        return key;
    }

    @Override
    public int getAlarmType() {
        return Alarm.buildType(key, type);
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getSerialNo() {
        return alarmId.getSerialNo();
    }

    @Override
    public int getFileTotal() {
        return alarmId.getFileTotal();
    }

    @Override
    public String getExtra() {
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getStatusBit() {
        return statusBit;
    }

    public void setStatusBit(int statusBit) {
        this.statusBit = statusBit;
    }

    public AlarmId getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(AlarmId alarmId) {
        this.alarmId = alarmId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(300);
        sb.append("AlarmBSD{id=").append(id);
        sb.append(", state=").append(state);
        sb.append(", type=").append(type);
        sb.append(", speed=").append(speed);
        sb.append(", altitude=").append(altitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", statusBit=").append(statusBit);
        sb.append(", alarmId=").append(alarmId);
        sb.append('}');
        return sb.toString();
    }
}
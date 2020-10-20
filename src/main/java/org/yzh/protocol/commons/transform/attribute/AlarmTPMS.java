package org.yzh.protocol.commons.transform.attribute;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.commons.transform.Attribute;
import org.yzh.protocol.jsatl12.AlarmId;

import java.time.LocalDateTime;

/**
 * 轮胎气压监测系统
 */
public class AlarmTPMS extends Attribute {

    public static final int attributeId = 0x66;

    private long serialNo;
    private int state;
    private int speed;
    private int altitude;
    private int latitude;
    private int longitude;
    private LocalDateTime dateTime;
    private int status;
    private AlarmId alarmId;

    @Override
    public int getAttributeId() {
        return attributeId;
    }

    @Field(index = 0, type = DataType.DWORD, desc = "报警ID")
    public long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "标志状态")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Field(index = 5, type = DataType.BYTE, desc = "车速")
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Field(index = 6, type = DataType.WORD, desc = "高程")
    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    @Field(index = 8, type = DataType.DWORD, desc = "纬度")
    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    @Field(index = 12, type = DataType.DWORD, desc = "经度")
    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    @Field(index = 16, type = DataType.BCD8421, length = 6, desc = "日期时间")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Field(index = 22, type = DataType.WORD, desc = "车辆状态")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Field(index = 24, type = DataType.OBJ, length = 16, desc = "报警标识号")
    public AlarmId getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(AlarmId alarmId) {
        this.alarmId = alarmId;
    }
}
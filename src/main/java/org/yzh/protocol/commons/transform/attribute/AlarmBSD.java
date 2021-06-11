package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import org.yzh.protocol.jsatl12.AlarmId;

import java.time.LocalDateTime;

/**
 * 盲点监测
 */
public class AlarmBSD implements Alarm {

    public static final int id = 0x67;

    public static int id() {
        return id;
    }

    private long serialNo;
    private int state;
    private int type;
    private int speed;
    private int altitude;
    private int latitude;
    private int longitude;
    private LocalDateTime dateTime;
    private int status;
    private AlarmId alarmId;

    @Override
    public int getLevel() {
        return 0;
    }

    @Field(index = 0, type = DataType.DWORD, desc = "报警ID")
    public long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    /** 该字段仅适用于有开始和结束标志类型的报警或事件,报警类型或事件类型无开始和结束标志,则该位不可用,填入0x00即可 */
    @Field(index = 4, type = DataType.BYTE, desc = "标志状态: 0.不可用 1.开始标志 2.结束标志")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Field(index = 5, type = DataType.BYTE, desc = "报警/事件类型:\n" +
            "0x01:后方接近报警\n" +
            "0x02:左侧后方接近报警\n" +
            "0x03:右侧后方接近报警")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 6, type = DataType.BYTE, desc = "车速")
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Field(index = 7, type = DataType.WORD, desc = "高程")
    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    @Field(index = 9, type = DataType.DWORD, desc = "纬度")
    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    @Field(index = 13, type = DataType.DWORD, desc = "经度")
    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    @Field(index = 17, type = DataType.BCD8421, length = 6, desc = "日期时间")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Field(index = 23, type = DataType.WORD, desc = "车辆状态")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Field(index = 25, type = DataType.OBJ, length = 16, desc = "报警标识号")
    public AlarmId getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(AlarmId alarmId) {
        this.alarmId = alarmId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AlarmBSD{");
        sb.append("serialNo=").append(serialNo);
        sb.append(", state=").append(state);
        sb.append(", type=").append(type);
        sb.append(", speed=").append(speed);
        sb.append(", altitude=").append(altitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", status=").append(status);
        sb.append(", alarmId=").append(alarmId);
        sb.append('}');
        return sb.toString();
    }
}
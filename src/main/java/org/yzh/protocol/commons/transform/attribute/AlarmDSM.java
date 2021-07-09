package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import org.yzh.protocol.jsatl12.AlarmId;

import java.time.LocalDateTime;

/**
 * 驾驶员状态监测
 */
public class AlarmDSM implements Alarm {

    public static final int id = 0x65;

    public static int id() {
        return id;
    }

    @Field(index = 0, type = DataType.DWORD, desc = "报警ID")
    private long serialNo;
    @Field(index = 4, type = DataType.BYTE, desc = "标志状态：0.不可用 1.开始标志 2.结束标志")
    private int state;
    @Field(index = 5, type = DataType.BYTE, desc = "报警/事件类型：" +
            " 1.疲劳驾驶报警" +
            " 2.接打电话报警" +
            " 3.抽烟报警" +
            " 4.分神驾驶报警|不目视前方报警(粤标)" +
            " 5.驾驶员异常报警" +
            " 6.探头遮挡报警(粤标)" +
            " 7.用户自定义" +
            " 8.超时驾驶报警(粤标)" +
            " 9.用户自定义" +
            " 10.未系安全带报警(粤标)" +
            " 11.红外阻断型墨镜失效报警(粤标)" +
            " 12.双脱把报警(双手同时脱离方向盘)(粤标)" +
            " 13.玩手机报警(粤标)" +
            " 14.用户自定义" +
            " 15.用户自定义" +
            " 16.自动抓拍事件" +
            " 17.驾驶员变更事件" +
            " 18~31.用户自定义")
    private int type;
    @Field(index = 6, type = DataType.BYTE, desc = "报警级别")
    private int level;
    @Field(index = 7, type = DataType.BYTE, desc = "疲劳程度")
    private int fatigueDegree;
    @Field(index = 8, type = DataType.DWORD, desc = "预留")
    private int reserved;
    @Field(index = 12, type = DataType.BYTE, desc = "车速")
    private int speed;
    @Field(index = 13, type = DataType.WORD, desc = "高程")
    private int altitude;
    @Field(index = 15, type = DataType.DWORD, desc = "纬度")
    private int latitude;
    @Field(index = 19, type = DataType.DWORD, desc = "经度")
    private int longitude;
    @Field(index = 23, type = DataType.BCD8421, length = 6, desc = "日期时间")
    private LocalDateTime dateTime;
    @Field(index = 29, type = DataType.WORD, desc = "车辆状态")
    private int status;
    @Field(index = 31, type = DataType.OBJ, length = 16, desc = "报警标识号", version = {-1, 0})
    @Field(index = 31, type = DataType.OBJ, length = 40, desc = "报警标识号(粤标)", version = 1)
    private AlarmId alarmId;

    public long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFatigueDegree() {
        return fatigueDegree;
    }

    public void setFatigueDegree(int fatigueDegree) {
        this.fatigueDegree = fatigueDegree;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AlarmId getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(AlarmId alarmId) {
        this.alarmId = alarmId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(400);
        sb.append("AlarmDSM{serialNo=").append(serialNo);
        sb.append(", state=").append(state);
        sb.append(", type=").append(type);
        sb.append(", level=").append(level);
        sb.append(", fatigueDegree=").append(fatigueDegree);
        sb.append(", reserved=").append(reserved);
        sb.append(", speed=").append(speed);
        sb.append(", altitude=").append(altitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", status=").append(status);
        sb.append(", alarmId=").append(alarmId);
        sb.append('}');
        return sb.toString();
    }
}
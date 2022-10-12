package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.annotation.Field;

import java.time.LocalDateTime;

/**
 * 驾驶员状态监测 0x65
 */
public class AlarmDSM extends Alarm {

    public static final int key = 101;

    @Field(length = 4, desc = "报警ID")
    private long id;
    @Field(length = 1, desc = "标志状态：0.不可用 1.开始标志 2.结束标志")
    private int state;
    @Field(length = 1, desc = "报警/事件类型：" +
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
    @Field(length = 1, desc = "报警级别")
    private int level;
    @Field(length = 1, desc = "疲劳程度")
    private int fatigueDegree;
    @Field(length = 4, desc = "预留")
    private int reserves;
    @Field(length = 1, desc = "车速")
    private int speed;
    @Field(length = 2, desc = "高程")
    private int altitude;
    @Field(length = 4, desc = "纬度")
    private int latitude;
    @Field(length = 4, desc = "经度")
    private int longitude;
    @Field(length = 6, charset = "BCD", desc = "日期时间")
    private LocalDateTime alarmTime;
    @Field(length = 2, desc = "车辆状态")
    private int statusBit;

    @Field(length = 7, desc = "终端ID", version = {-1, 0})
    @Field(length = 30, desc = "终端ID(粤标)", version = 1)
    private String deviceId;
    @Field(length = 6, charset = "BCD", desc = "时间(YYMMDDHHMMSS)")
    private LocalDateTime dateTime;
    @Field(length = 1, desc = "序号(同一时间点报警的序号，从0循环累加)")
    private int sequenceNo;
    @Field(length = 1, desc = "附件数量")
    private int fileTotal;
    @Field(length = 1, desc = "预留", version = {-1, 0})
    @Field(length = 2, desc = "预留(粤标)", version = 1)
    private int reserved;

    @Override
    public int getSource() {
        return 1;
    }

    @Override
    public int getCategory() {
        return key;
    }

    @Override
    public int getAlarmType() {
        return Alarm.buildType(key, type);
    }

    @Override
    public String getExtra() {
        return "fatigueDegree:" + fatigueDegree;
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

    public int getReserves() {
        return reserves;
    }

    public void setReserves(int reserves) {
        this.reserves = reserves;
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

    public LocalDateTime getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(LocalDateTime alarmTime) {
        this.alarmTime = alarmTime;
    }

    public int getStatusBit() {
        return statusBit;
    }

    public void setStatusBit(int statusBit) {
        this.statusBit = statusBit;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public int getFileTotal() {
        return fileTotal;
    }

    public void setFileTotal(int fileTotal) {
        this.fileTotal = fileTotal;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(400);
        sb.append("AlarmDSM{id=").append(id);
        sb.append(", state=").append(state);
        sb.append(", type=").append(type);
        sb.append(", level=").append(level);
        sb.append(", fatigueDegree=").append(fatigueDegree);
        sb.append(", reserves=").append(reserves);
        sb.append(", speed=").append(speed);
        sb.append(", altitude=").append(altitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", alarmTime=").append(alarmTime);
        sb.append(", statusBit=").append(statusBit);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", sequenceNo=").append(sequenceNo);
        sb.append(", fileTotal=").append(fileTotal);
        sb.append(", reserved=").append(reserved);
        sb.append('}');
        return sb.toString();
    }
}
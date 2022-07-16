package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.annotation.Field;
import org.yzh.protocol.jsatl12.AlarmId;

import java.time.LocalDateTime;

/**
 * 高级驾驶辅助系统报警 0x64
 */
public class AlarmADAS implements Alarm {

    public static final int key = 100;

    @Field(length = 4, desc = "报警ID")
    private long id;
    @Field(length = 1, desc = "标志状态：0.不可用 1.开始标志 2.结束标志")
    private int state;
    @Field(length = 1, desc = "报警/事件类型：" +
            " 1.前向碰撞报警" +
            " 2.车道偏离报警" +
            " 3.车距过近报警" +
            " 4.行人碰撞报警" +
            " 5.频繁变道报警" +
            " 6.道路标识超限报警" +
            " 7.障碍物报警" +
            " 8~15.用户自定义" +
            " 16.道路标志识别事件" +
            " 17.主动抓拍事件" +
            " 18.实线变道报警(粤标)" +
            " 19.车厢过道行人检测报警(粤标)" +
            " 18~31.用户自定义")
    private int type;
    @Field(length = 1, desc = "报警级别")
    private int level;
    @Field(length = 1, desc = "前车车速(Km/h)范围0~250,仅报警类型为1和2时有效")
    private int frontSpeed;
    @Field(length = 1, desc = "前车/行人距离(100ms),范围0~100,仅报警类型为1、2和4时有效")
    private int frontDistance;
    @Field(length = 1, desc = "偏离类型：1.左侧偏离 2.右侧偏离(报警类型为2时有效)")
    private int deviateType;
    @Field(length = 1, desc = "道路标志识别类型：1.限速标志 2.限高标志 3.限重标志 4.禁行标志(粤标) 5.禁停标志(粤标)(报警类型为6和10时有效)")
    private int roadSign;
    @Field(length = 1, desc = "道路标志识别数据")
    private int roadSignValue;
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
    public int getSerialNo() {
        return alarmId.getSerialNo();
    }

    @Override
    public int getFileTotal() {
        return alarmId.getFileTotal();
    }

    @Override
    public String getExtra() {
        final StringBuilder sb = new StringBuilder(64);
        if (type == 1 || type == 2) {
            sb.append("frontSpeed:").append(frontSpeed).append(',');
        }
        if (type == 1 || type == 2 || type == 4) {
            sb.append("frontDistance:").append(frontDistance).append(',');
        }
        if (type == 2) {
            sb.append("deviateType:").append(deviateType).append(',');
        }
        if (type == 6 || type == 10) {
            sb.append("roadSign:").append(roadSign).append(',');
            sb.append("roadSignValue:").append(roadSignValue).append(',');
        }
        int length = sb.length();
        if (length > 0)
            return sb.substring(0, length - 1);
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFrontSpeed() {
        return frontSpeed;
    }

    public void setFrontSpeed(int frontSpeed) {
        this.frontSpeed = frontSpeed;
    }

    public int getFrontDistance() {
        return frontDistance;
    }

    public void setFrontDistance(int frontDistance) {
        this.frontDistance = frontDistance;
    }

    public int getDeviateType() {
        return deviateType;
    }

    public void setDeviateType(int deviateType) {
        this.deviateType = deviateType;
    }

    public int getRoadSign() {
        return roadSign;
    }

    public void setRoadSign(int roadSign) {
        this.roadSign = roadSign;
    }

    public int getRoadSignValue() {
        return roadSignValue;
    }

    public void setRoadSignValue(int roadSignValue) {
        this.roadSignValue = roadSignValue;
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

    public AlarmId getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(AlarmId alarmId) {
        this.alarmId = alarmId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(512);
        sb.append("AlarmADAS{id=").append(id);
        sb.append(", state=").append(state);
        sb.append(", type=").append(type);
        sb.append(", level=").append(level);
        sb.append(", frontSpeed=").append(frontSpeed);
        sb.append(", frontDistance=").append(frontDistance);
        sb.append(", deviateType=").append(deviateType);
        sb.append(", roadSign=").append(roadSign);
        sb.append(", roadSignValue=").append(roadSignValue);
        sb.append(", speed=").append(speed);
        sb.append(", altitude=").append(altitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", alarmTime=").append(alarmTime);
        sb.append(", statusBit=").append(statusBit);
        sb.append(", alarmId=").append(alarmId);
        sb.append('}');
        return sb.toString();
    }
}
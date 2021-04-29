package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import org.yzh.protocol.jsatl12.AlarmId;

import java.time.LocalDateTime;

/**
 * 高级驾驶辅助系统报警
 */
public class AlarmADAS {

    public static final int id = 0x64;

    public static int id() {
        return id;
    }

    private long serialNo;
    private int state;
    private int type;
    private int level;
    private int frontSpeed;
    private int frontDistance;
    private int deviateType;
    private int roadSign;
    private int roadSignValue;
    private int speed;
    private int altitude;
    private int latitude;
    private int longitude;
    private LocalDateTime dateTime;
    private int status;
    private AlarmId alarmId;

    @Field(index = 0, type = DataType.DWORD, desc = "报警ID")
    public long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * 0x00:不可用
     * 0x01:开始标志
     * 0x02:结束标志
     * 该字段仅适用于有开始和结束标志类型的报警或事件,
     * 报警类型或事件类型无开始和结束标志，则该位不可
     * 用，填入0x00即可。
     */
    @Field(index = 4, type = DataType.BYTE, desc = "标志状态")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * 0x01:前向碰撞报警
     * 0x02:车道偏离报警
     * 0x03:车距过近报警
     * 0x04:行人碰撞报警
     * 0x05:频繁变道报警
     * 0x06:道路标识超限报警
     * 0x07:障碍物报警
     * 0x08^0x0F:用户自定义
     * 0x10:道路标志识别事件
     * 0x11:主动抓拍事件
     * 0x12~0x1F:用户自定义
     */
    @Field(index = 5, type = DataType.BYTE, desc = "报警/事件类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 6, type = DataType.BYTE, desc = "报警级别")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Field(index = 7, type = DataType.BYTE, desc = "前车车速(Km/h)范围0^250，仅报警类型为1和2时有效。")
    public int getFrontSpeed() {
        return frontSpeed;
    }

    public void setFrontSpeed(int frontSpeed) {
        this.frontSpeed = frontSpeed;
    }

    @Field(index = 8, type = DataType.BYTE, desc = "前车/行人距离(100ms)，范围0^100，仅报警类型为1、2和4时有效。")
    public int getFrontDistance() {
        return frontDistance;
    }

    public void setFrontDistance(int frontDistance) {
        this.frontDistance = frontDistance;
    }

    @Field(index = 9, type = DataType.BYTE, desc = "偏离类型:1.左侧偏离 2.右侧偏离(报警类型为2时有效)")
    public int getDeviateType() {
        return deviateType;
    }

    public void setDeviateType(int deviateType) {
        this.deviateType = deviateType;
    }

    @Field(index = 10, type = DataType.BYTE, desc = "道路标志识别类型:1.限速标志 2.限高标志 3.限重标志(报警类型为6和10时有效)")
    public int getRoadSign() {
        return roadSign;
    }

    public void setRoadSign(int roadSign) {
        this.roadSign = roadSign;
    }

    @Field(index = 11, type = DataType.BYTE, desc = "道路标志识别数据")
    public int getRoadSignValue() {
        return roadSignValue;
    }

    public void setRoadSignValue(int roadSignValue) {
        this.roadSignValue = roadSignValue;
    }

    @Field(index = 12, type = DataType.BYTE, desc = "车速")
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Field(index = 13, type = DataType.WORD, desc = "高程")
    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    @Field(index = 15, type = DataType.DWORD, desc = "纬度")
    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    @Field(index = 19, type = DataType.DWORD, desc = "经度")
    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    @Field(index = 23, type = DataType.BCD8421, length = 6, desc = "日期时间")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Field(index = 29, type = DataType.WORD, desc = "车辆状态")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Field(index = 31, type = DataType.OBJ, length = 16, desc = "报警标识号")
    public AlarmId getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(AlarmId alarmId) {
        this.alarmId = alarmId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AlarmADAS{");
        sb.append("serialNo=").append(serialNo);
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
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", status=").append(status);
        sb.append(", alarmId=").append(alarmId);
        sb.append('}');
        return sb.toString();
    }
}
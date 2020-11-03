package org.yzh.protocol.commons.transform.attribute;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import org.yzh.protocol.commons.transform.Attribute;
import org.yzh.protocol.jsatl12.AlarmId;

import java.time.LocalDateTime;

/**
 * 高级驾驶辅助系统报警
 */
public class AlarmADAS extends Attribute {

    public static final int attributeId = 0x64;

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

    @Field(index = 7, type = DataType.BYTE, desc = "前车车速")
    public int getFrontSpeed() {
        return frontSpeed;
    }

    public void setFrontSpeed(int frontSpeed) {
        this.frontSpeed = frontSpeed;
    }

    @Field(index = 8, type = DataType.BYTE, desc = "前车/行人距离")
    public int getFrontDistance() {
        return frontDistance;
    }

    public void setFrontDistance(int frontDistance) {
        this.frontDistance = frontDistance;
    }

    @Field(index = 9, type = DataType.BYTE, desc = "偏离类型")
    public int getDeviateType() {
        return deviateType;
    }

    public void setDeviateType(int deviateType) {
        this.deviateType = deviateType;
    }

    @Field(index = 10, type = DataType.BYTE, desc = "道路标志识别类型")
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
}
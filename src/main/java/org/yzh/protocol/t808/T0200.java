package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Convert;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.transform.AttributeConverter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.位置信息汇报)
public class T0200 extends JTMessage {

    private int warnBit;
    private int statusBit;
    private int latitude;
    private int longitude;
    private int altitude;
    private int speed;
    private int direction;
    private LocalDateTime dateTime;
    private Map<Integer, Object> attributes;

    @Field(index = 0, type = DataType.DWORD, desc = "报警标志")
    public int getWarnBit() {
        return warnBit;
    }

    public void setWarnBit(int warnBit) {
        this.warnBit = warnBit;
    }

    @Field(index = 4, type = DataType.DWORD, desc = "状态")
    public int getStatusBit() {
        return statusBit;
    }

    public void setStatusBit(int statusBit) {
        this.statusBit = statusBit;
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

    @Field(index = 16, type = DataType.WORD, desc = "海拔")
    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    @Field(index = 18, type = DataType.WORD, desc = "速度(1/10公里每小时)")
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Field(index = 20, type = DataType.WORD, desc = "方向")
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Field(index = 22, type = DataType.BCD8421, length = 6, desc = "时间")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Convert(converter = AttributeConverter.class)
    @Field(index = 28, type = DataType.MAP, desc = "位置附加信息")
    public Map<Integer, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<Integer, Object> attributes) {
        this.attributes = attributes;
    }
}
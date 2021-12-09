package org.yzh.protocol.t808;

import io.github.yezhihao.netmc.core.model.Response;
import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.transform.AttributeConverter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message({JT808.位置信息查询应答, JT808.车辆控制应答})
public class T0201_0500 extends JTMessage implements Response {

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    private int responseSerialNo;
    @Field(index = 2, type = DataType.DWORD, desc = "报警标志")
    private int warnBit;
    @Field(index = 6, type = DataType.DWORD, desc = "状态")
    private int statusBit;
    @Field(index = 10, type = DataType.DWORD, desc = "纬度")
    private int latitude;
    @Field(index = 14, type = DataType.DWORD, desc = "经度")
    private int longitude;
    @Field(index = 18, type = DataType.WORD, desc = "高程(米)")
    private int altitude;
    @Field(index = 20, type = DataType.WORD, desc = "速度(1/10公里每小时)")
    private int speed;
    @Field(index = 22, type = DataType.WORD, desc = "方向")
    private int direction;
    @Field(index = 24, type = DataType.BCD8421, length = 6, desc = "时间(YYMMDDHHMMSS)")
    private LocalDateTime dateTime;
    @Field(index = 30, type = DataType.MAP, desc = "位置附加信息", converter = AttributeConverter.class)
    private Map<Integer, Object> attributes;

    public int getResponseSerialNo() {
        return responseSerialNo;
    }

    public void setResponseSerialNo(int responseSerialNo) {
        this.responseSerialNo = responseSerialNo;
    }

    public int getWarnBit() {
        return warnBit;
    }

    public void setWarnBit(int warnBit) {
        this.warnBit = warnBit;
    }

    public int getStatusBit() {
        return statusBit;
    }

    public void setStatusBit(int statusBit) {
        this.statusBit = statusBit;
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

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Map<Integer, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<Integer, Object> attributes) {
        this.attributes = attributes;
    }
}
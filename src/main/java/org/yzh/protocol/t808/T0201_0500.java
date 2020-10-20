package org.yzh.protocol.t808;

import org.yzh.framework.mvc.model.Response;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.BytesAttribute;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message({JT808.位置信息查询应答, JT808.车辆控制应答})
public class T0201_0500 extends JTMessage implements Response {

    private int serialNo;
    private int warningMark;
    private int status;
    private int latitude;
    private int longitude;
    private int altitude;
    private int speed;
    private int direction;
    private LocalDateTime dateTime;

    private List<BytesAttribute> bytesAttributes;

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 2, type = DataType.DWORD, desc = "报警标志")
    public int getWarningMark() {
        return warningMark;
    }

    public void setWarningMark(int warningMark) {
        this.warningMark = warningMark;
    }

    @Field(index = 6, type = DataType.DWORD, desc = "状态")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Field(index = 10, type = DataType.DWORD, desc = "纬度")
    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    @Field(index = 14, type = DataType.DWORD, desc = "经度")
    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    @Field(index = 18, type = DataType.WORD, desc = "海拔")
    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    @Field(index = 20, type = DataType.WORD, desc = "速度")
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Field(index = 22, type = DataType.WORD, desc = "方向")
    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Field(index = 24, type = DataType.BCD8421, length = 6, desc = "时间")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Field(index = 30, type = DataType.LIST, desc = "位置附加信息")
    public List<BytesAttribute> getBytesAttributes() {
        return bytesAttributes;
    }

    public void setBytesAttributes(List<BytesAttribute> bytesAttributes) {
        this.bytesAttributes = bytesAttributes;
    }
}
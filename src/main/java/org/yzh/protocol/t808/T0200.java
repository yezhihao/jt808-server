package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.BytesAttribute;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.transform.PositionAttributeUtils;
import org.yzh.protocol.commons.transform.Attribute;

import java.util.List;
import java.util.Map;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.位置信息汇报)
public class T0200 extends AbstractMessage<Header> {

    private int warningMark;
    private int status;
    private int latitude;
    private int longitude;
    private int altitude;
    private int speed;
    private int direction;
    private String dateTime;
    private List<BytesAttribute> bytesAttributes;

    @Field(index = 0, type = DataType.DWORD, desc = "报警标志")
    public int getWarningMark() {
        return warningMark;
    }

    public void setWarningMark(int warningMark) {
        this.warningMark = warningMark;
    }

    @Field(index = 4, type = DataType.DWORD, desc = "状态")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    @Field(index = 18, type = DataType.WORD, desc = "速度")
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
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Field(index = 28, type = DataType.LIST, desc = "位置附加信息")
    public List<BytesAttribute> getBytesAttributes() {
        return bytesAttributes;
    }

    public void setBytesAttributes(List<BytesAttribute> bytesAttributes) {
        this.bytesAttributes = bytesAttributes;
    }

    public Map<Integer, Attribute> getAttributes() {
        return PositionAttributeUtils.transform(bytesAttributes);
    }

    public void setAttributes(Map<Integer, Attribute> attributes) {
        this.bytesAttributes = PositionAttributeUtils.transform(attributes);
    }
}
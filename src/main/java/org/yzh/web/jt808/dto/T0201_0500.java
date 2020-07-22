package org.yzh.web.jt808.dto;

import org.yzh.framework.orm.annotation.Property;
import org.yzh.framework.orm.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.model.AbstractBody;
import org.yzh.web.jt808.common.JT808;
import org.yzh.web.jt808.dto.basics.BytesAttribute;

import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
@Type({JT808.位置信息查询应答, JT808.车辆控制应答})
public class T0201_0500 extends AbstractBody {

    private Integer serialNo;
    private Integer warningMark;
    private Integer status;
    private Integer latitude;
    private Integer longitude;
    private Integer altitude;
    private Integer speed;
    private Integer direction;
    private String dateTime;

    private List<BytesAttribute> bytesAttributes;

    @Property(index = 0, type = DataType.WORD, desc = "应答流水号")
    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    @Property(index = 2, type = DataType.DWORD, desc = "报警标志")
    public Integer getWarningMark() {
        return warningMark;
    }

    public void setWarningMark(Integer warningMark) {
        this.warningMark = warningMark;
    }

    @Property(index = 6, type = DataType.DWORD, desc = "状态")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Property(index = 10, type = DataType.DWORD, desc = "纬度")
    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    @Property(index = 14, type = DataType.DWORD, desc = "经度")
    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    @Property(index = 18, type = DataType.WORD, desc = "海拔")
    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    @Property(index = 20, type = DataType.WORD, desc = "速度")
    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Property(index = 22, type = DataType.WORD, desc = "方向")
    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    @Property(index = 24, type = DataType.BCD8421, length = 6, desc = "时间")
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Property(index = 30, type = DataType.LIST, desc = "位置附加信息")
    public List<BytesAttribute> getBytesAttributes() {
        return bytesAttributes;
    }

    public void setBytesAttributes(List<BytesAttribute> bytesAttributes) {
        this.bytesAttributes = bytesAttributes;
    }
}
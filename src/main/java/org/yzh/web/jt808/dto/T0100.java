package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Ps;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

@Type(MessageId.终端注册)
public class T0100 extends AbstractBody {

    private Integer provinceId;
    private Integer cityId;
    private String manufacturerId;
    private String terminalType;
    private String terminalId;
    private Integer licensePlateColor;
    private String licensePlate;

    /** 设备安装车辆所在的省域,省域ID采用GB/T2260中规定的行政区划代码6位中前两位 */
    @Property(index = 0, type = DataType.WORD, desc = "省域ID", version = {0, 1})
    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    /** 设备安装车辆所在的市域或县域,市县域ID采用GB/T2260中规定的行政区划代码6位中后四位 */
    @Property(index = 2, type = DataType.WORD, desc = "市县域ID", version = {0, 1})
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /** 终端制造商编码 */
    @Ps({@Property(index = 4, type = DataType.BYTES, length = 5, desc = "制造商ID", version = 0),
            @Property(index = 4, type = DataType.BYTES, length = 11, desc = "制造商ID", version = 1)})
    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    /** 由制造商自行定义,位数不足八位补空格 */
    @Ps({@Property(index = 9, type = DataType.BYTES, length = 20, desc = "终端型号", version = 0),
            @Property(index = 15, type = DataType.BYTES, length = 30, desc = "终端型号", version = 1)})
    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    /** 由大写字母和数字组成,此终端ID由制造商自行定义 */
    @Ps({@Property(index = 29, type = DataType.BYTES, length = 7, desc = "终端ID", version = 0),
            @Property(index = 45, type = DataType.BYTES, length = 30, desc = "终端ID", version = 1)})
    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    /** 按照JT/T415-2006的5.4.12（0:未上车牌,1:蓝色,2:黄色,3:黑色,4:白色,9:其他） */
    @Ps({@Property(index = 36, type = DataType.BYTE, desc = "车牌颜色", version = 0),
            @Property(index = 75, type = DataType.BYTE, desc = "车牌颜色", version = 1)})
    public Integer getLicensePlateColor() {
        return licensePlateColor;
    }

    public void setLicensePlateColor(Integer licensePlate) {
        this.licensePlateColor = licensePlate;
    }

    /** 车牌颜色为0时,表示车辆VIN；否则,表示公安交通管理部门颁发的机动车号牌 */
    @Ps({@Property(index = 37, type = DataType.STRING, desc = "车辆标识", version = 0),
            @Property(index = 76, type = DataType.STRING, desc = "车辆标识", version = 1)})
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
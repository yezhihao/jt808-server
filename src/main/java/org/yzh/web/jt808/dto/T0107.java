package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
@Type(MessageId.查询终端属性应答)
public class T0107 extends AbstractBody {

    private Integer type;
    private String manufacturerId;
    private String terminalType;
    private String terminalId;
    private String simId;
    private Integer hardwareVersionLen;
    private String hardwareVersion;
    private String firmwareVersionLen;
    private String firmwareVersion;
    private Integer gnssAttribute;
    private Integer networkAttribute;

    @Property(index = 0, type = DataType.BYTE, desc = "终端类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 1, type = DataType.STRING, length = 7, pad = 32, desc = "制造商ID,终端制造商编码")
    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Property(index = 8, type = DataType.STRING, length = 20, pad = 32, desc = "终端型号,由制造商自行定义,位数不足八位补空格")
    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    @Property(index = 27, type = DataType.STRING, length = 7, desc = "终端ID,由大写字母和数字组成,此终端ID由制造商自行定义")
    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    @Property(index = 34, type = DataType.BCD8421, length = 10, desc = "终端SIM卡ICCID")
    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    @Property(index = 45, type = DataType.BYTE, desc = "硬件版本号长度")
    public Integer getHardwareVersionLen() {
        return hardwareVersionLen;
    }

    public void setHardwareVersionLen(Integer hardwareVersionLen) {
        this.hardwareVersionLen = hardwareVersionLen;
    }

    @Property(index = 46, type = DataType.STRING, lengthName = "hardwareVersionLen", desc = "硬件版本号")
    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    @Property(index = 46, indexOffsetName = "hardwareVersionLen", type = DataType.BYTE, desc = "固件版本号长度")
    public String getFirmwareVersionLen() {
        return firmwareVersionLen;
    }

    public void setFirmwareVersionLen(String firmwareVersionLen) {
        this.firmwareVersionLen = firmwareVersionLen;
    }

    @Property(index = 47, type = DataType.STRING, lengthName = "firmwareVersionLen", desc = "固件版本号")
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    @Property(index = 46, indexOffsetName = {"hardwareVersionLen", "firmwareVersionLen"}, type = DataType.BYTE, desc = "GNSS模块属性")
    public Integer getGnssAttribute() {
        return gnssAttribute;
    }

    public void setGnssAttribute(Integer gnssAttribute) {
        this.gnssAttribute = gnssAttribute;
    }

    @Property(index = 47, indexOffsetName = {"hardwareVersionLen", "firmwareVersionLen"}, type = DataType.BYTE, desc = "通信模块属性")
    public Integer getNetworkAttribute() {
        return networkAttribute;
    }

    public void setNetworkAttribute(Integer networkAttribute) {
        this.networkAttribute = networkAttribute;
    }
}
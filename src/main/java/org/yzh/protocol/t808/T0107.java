package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.查询终端属性应答)
public class T0107 extends AbstractMessage<Header> {

    private int type;
    private String manufacturerId;
    private String terminalType;
    private String terminalId;
    private String simId;
    private int hardwareVersionLen;
    private String hardwareVersion;
    private int firmwareVersionLen;
    private String firmwareVersion;
    private int gnssAttribute;
    private int networkAttribute;

    @Field(index = 0, type = DataType.BYTE, desc = "终端类型")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.STRING, length = 7, pad = 32, desc = "制造商ID,终端制造商编码")
    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Field(index = 8, type = DataType.STRING, length = 20, pad = 32, desc = "终端型号,由制造商自行定义,位数不足八位补空格")
    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    @Field(index = 27, type = DataType.STRING, length = 7, desc = "终端ID,由大写字母和数字组成,此终端ID由制造商自行定义")
    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    @Field(index = 34, type = DataType.BCD8421, length = 10, desc = "终端SIM卡ICCID")
    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    @Field(index = 45, type = DataType.BYTE, desc = "硬件版本号长度")
    public int getHardwareVersionLen() {
        return hardwareVersionLen;
    }

    public void setHardwareVersionLen(int hardwareVersionLen) {
        this.hardwareVersionLen = hardwareVersionLen;
    }

    @Field(index = 46, type = DataType.STRING, lengthName = "hardwareVersionLen", desc = "硬件版本号")
    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
        this.hardwareVersionLen = hardwareVersion.length();
    }

    @Field(index = 46, indexOffsetName = "hardwareVersionLen", type = DataType.BYTE, desc = "固件版本号长度")
    public int getFirmwareVersionLen() {
        return firmwareVersionLen;
    }

    public void setFirmwareVersionLen(int firmwareVersionLen) {
        this.firmwareVersionLen = firmwareVersionLen;
    }

    @Field(index = 47, type = DataType.STRING, lengthName = "firmwareVersionLen", desc = "固件版本号")
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
        this.firmwareVersionLen = firmwareVersion.length();
    }

    @Field(index = 46, indexOffsetName = {"hardwareVersionLen", "firmwareVersionLen"}, type = DataType.BYTE, desc = "GNSS模块属性")
    public int getGnssAttribute() {
        return gnssAttribute;
    }

    public void setGnssAttribute(int gnssAttribute) {
        this.gnssAttribute = gnssAttribute;
    }

    @Field(index = 47, indexOffsetName = {"hardwareVersionLen", "firmwareVersionLen"}, type = DataType.BYTE, desc = "通信模块属性")
    public int getNetworkAttribute() {
        return networkAttribute;
    }

    public void setNetworkAttribute(int networkAttribute) {
        this.networkAttribute = networkAttribute;
    }
}
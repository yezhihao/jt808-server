package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.查询终端属性应答)
public class T0107 extends AbstractMessage<Header> {

    private int deviceType;
    private String makerId;
    private String deviceModel;
    private String deviceId;
    private String simNo;
    private int hardwareVersionLen;
    private String hardwareVersion;
    private int firmwareVersionLen;
    private String firmwareVersion;
    private int gnssAttribute;
    private int networkAttribute;

    @Field(index = 0, type = DataType.BYTE, desc = "终端类型")
    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    @Field(index = 1, type = DataType.STRING, length = 7, desc = "制造商ID,终端制造商编码")
    public String getMakerId() {
        return makerId;
    }

    public void setMakerId(String makerId) {
        this.makerId = makerId;
    }

    /** 由制造商自行定义,位数不足时，后补"0x00" */
    @Field(index = 8, type = DataType.STRING, length = 20, desc = "终端型号")
    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    /** 由大写字母和数字组成,此终端ID由制造商自行定义,位数不足时，后补"0x00" */
    @Field(index = 27, type = DataType.STRING, length = 7, desc = "终端ID")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Field(index = 34, type = DataType.BCD8421, length = 10, desc = "终端SIM卡ICCID")
    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
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
package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.查询终端属性应答)
public class T0107 extends JTMessage {

    @Field(length = 2, desc = "终端类型", version = {0, 1})
    private int deviceType;
    @Field(length = 5, desc = "制造商ID,终端制造商编码", version = {0, 1})
    private String makerId;
    @Field(length = 20, desc = "终端型号", version = 0)
    @Field(length = 30, desc = "终端型号", version = 1)
    private String deviceModel;
    @Field(length = 7, desc = "终端ID", version = 0)
    @Field(length = 30, desc = "终端ID", version = 1)
    private String deviceId;
    @Field(length = 10, charset = "HEX", desc = "终端SIM卡ICCID", version = 0)
    @Field(length = 10, charset = "HEX", desc = "终端SIM卡ICCID", version = 1)
    private String iccid;
    @Field(lengthUnit = 1, desc = "硬件版本号", version = 0)
    @Field(lengthUnit = 1, desc = "硬件版本号", version = 1)
    private String hardwareVersion;
    @Field(lengthUnit = 1, desc = "固件版本号", version = 0)
    @Field(lengthUnit = 1, desc = "固件版本号", version = 1)
    private String firmwareVersion;
    @Field(length = 1, desc = "GNSS模块属性", version = 0)
    @Field(length = 1, desc = "GNSS模块属性", version = 1)
    private int gnssAttribute;
    @Field(length = 1, desc = "通信模块属性", version = 0)
    @Field(length = 1, desc = "通信模块属性", version = 1)
    private int networkAttribute;

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getMakerId() {
        return makerId;
    }

    public void setMakerId(String makerId) {
        this.makerId = makerId;
    }

    /** 由制造商自行定义,位数不足时,后补"0x00" */
    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    /** 由大写字母和数字组成,此终端ID由制造商自行定义,位数不足时,后补"0x00" */
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public int getGnssAttribute() {
        return gnssAttribute;
    }

    public void setGnssAttribute(int gnssAttribute) {
        this.gnssAttribute = gnssAttribute;
    }

    public int getNetworkAttribute() {
        return networkAttribute;
    }

    public void setNetworkAttribute(int networkAttribute) {
        this.networkAttribute = networkAttribute;
    }
}
package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.查询终端属性应答)
public class T0107 extends JTMessage {

    private int deviceType;
    private String makerId;
    private String deviceModel;
    private String deviceId;
    private String simNo;
    private String hardwareVersion;
    private String firmwareVersion;
    private int gnssAttribute;
    private int networkAttribute;

    @Field(index = 0, type = DataType.WORD, desc = "终端类型", version = {0, 1})
    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    @Field(index = 2, type = DataType.STRING, length = 5, desc = "制造商ID,终端制造商编码", version = {0, 1})
    public String getMakerId() {
        return makerId;
    }

    public void setMakerId(String makerId) {
        this.makerId = makerId;
    }

    /** 由制造商自行定义,位数不足时,后补"0x00" */
    @Field(index = 7, type = DataType.STRING, length = 20, desc = "终端型号", version = 0)
    @Field(index = 7, type = DataType.STRING, length = 30, desc = "终端型号", version = 1)
    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    /** 由大写字母和数字组成,此终端ID由制造商自行定义,位数不足时,后补"0x00" */
    @Field(index = 27, type = DataType.STRING, length = 7, desc = "终端ID", version = 0)
    @Field(index = 37, type = DataType.STRING, length = 30, desc = "终端ID", version = 1)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Field(index = 42, type = DataType.BCD8421, length = 10, desc = "终端SIM卡ICCID", version = 0)
    @Field(index = 67, type = DataType.BCD8421, length = 10, desc = "终端SIM卡ICCID", version = 1)
    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    @Field(index = 52, type = DataType.STRING, lengthSize = 1, desc = "硬件版本号", version = 0)
    @Field(index = 77, type = DataType.STRING, lengthSize = 1, desc = "硬件版本号", version = 1)
    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    @Field(index = 53, type = DataType.STRING, lengthSize = 1, desc = "固件版本号", version = 0)
    @Field(index = 78, type = DataType.STRING, lengthSize = 1, desc = "固件版本号", version = 1)
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    @Field(index = 54, type = DataType.BYTE, desc = "GNSS模块属性", version = 0)
    @Field(index = 79, type = DataType.BYTE, desc = "GNSS模块属性", version = 1)
    public int getGnssAttribute() {
        return gnssAttribute;
    }

    public void setGnssAttribute(int gnssAttribute) {
        this.gnssAttribute = gnssAttribute;
    }

    @Field(index = 55, type = DataType.BYTE, desc = "通信模块属性", version = 0)
    @Field(index = 80, type = DataType.BYTE, desc = "通信模块属性", version = 1)
    public int getNetworkAttribute() {
        return networkAttribute;
    }

    public void setNetworkAttribute(int networkAttribute) {
        this.networkAttribute = networkAttribute;
    }
}
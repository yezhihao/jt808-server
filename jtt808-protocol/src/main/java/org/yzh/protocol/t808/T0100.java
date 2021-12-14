package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.终端注册)
public class T0100 extends JTMessage {

    @Field(length = 2, desc = "省域ID")
    private int provinceId;
    @Field(length = 2, desc = "市县域ID")
    private int cityId;
    @Field(length = 5, desc = "制造商ID", version = {-1, 0})
    @Field(length = 11, desc = "制造商ID", version = 1)
    private String makerId;
    @Field(length = 8, desc = "终端型号", version = -1)
    @Field(length = 20, desc = "终端型号", version = 0)
    @Field(length = 30, desc = "终端型号", version = 1)
    private String deviceModel;
    @Field(length = 7, desc = "终端ID", version = {-1, 0})
    @Field(length = 30, desc = "终端ID", version = 1)
    private String deviceId;
    @Field(length = 1, desc = "车牌颜色：0.未上车牌 1.蓝色 2.黄色 3.黑色 4.白色 9.其他")
    private int plateColor;
    @Field(length = -1, desc = "车辆标识")
    private String plateNo;

    /** 设备安装车辆所在的省域,省域ID采用GB/T2260中规定的行政区划代码6位中前两位 */
    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    /** 设备安装车辆所在的市域或县域,市县域ID采用GB/T2260中规定的行政区划代码6位中后四位 */
    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    /** 终端制造商编码 */

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

    /** 由大写字母和数字组成,此终端ID由制造商自行定义 */
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /** 按照JT/T415-2006的5.4.12 */
    public int getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(int licensePlate) {
        this.plateColor = licensePlate;
    }

    /** 车牌颜色为0时,表示车辆VIN 否则,表示公安交通管理部门颁发的机动车号牌 */
    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    @Override
    public int getProtocolVersion() {
        int bodyLength = getBodyLength();
        if (bodyLength > 0 && bodyLength < 37)
            return -1;
        return super.getProtocolVersion();
    }
}
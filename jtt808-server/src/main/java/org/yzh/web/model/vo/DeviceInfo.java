package org.yzh.web.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.yzh.protocol.commons.Charsets;
import org.yzh.protocol.t808.T0200;

import java.io.*;
import java.time.LocalDate;

import static io.github.yezhihao.protostar.util.DateTool.BCD;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class DeviceInfo {

    @Schema(description = "签发日期")
    protected LocalDate issuedAt;
    @Schema(description = "预留字段")
    protected byte reserved;
    @Schema(description = "设备id")
    protected String deviceId;
    @Schema(description = "终端id")
    protected String mobileNo;
    @Schema(description = "机构id")
    protected int agencyId;
    @Schema(description = "司机id")
    protected int driverId;
    @Schema(description = "车辆id")
    protected int vehicleId;
    @Schema(description = "车牌颜色：1.蓝色 2.黄色 3.黑色 4.白色 9.其他")
    protected byte plateColor;
    @Schema(description = "车牌号")
    protected String plateNo;
    @Schema(description = "设备型号")
    protected String deviceModel;
    @Schema(description = "协议版本")
    protected int protocolVersion;

    @Schema(description = "实时状态")
    private T0200 deviceState;

    public DeviceInfo() {
    }

    public DeviceInfo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public LocalDate getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDate issuedAt) {
        this.issuedAt = issuedAt;
    }

    public byte getReserved() {
        return reserved;
    }

    public void setReserved(byte reserved) {
        this.reserved = reserved;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public byte getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(byte plateColor) {
        this.plateColor = plateColor;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public T0200 getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(T0200 deviceState) {
        this.deviceState = deviceState;
    }

    public static DeviceInfo formBytes(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             DataInputStream dis = new DataInputStream(bis)) {

            DeviceInfo result = new DeviceInfo();
            byte[] temp;
            dis.read(temp = new byte[3]);
            result.setIssuedAt(BCD.toDate(temp));
            result.setReserved(dis.readByte());
            int len = dis.readUnsignedByte();
            dis.read(temp = new byte[len]);
            result.setDeviceId(new String(temp, Charsets.GBK));

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toBytes(DeviceInfo deviceInfo) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(32);
             DataOutputStream dos = new DataOutputStream(bos)) {

            dos.write(BCD.from(deviceInfo.getIssuedAt()));
            dos.writeByte(deviceInfo.getReserved());
            byte[] bytes = deviceInfo.getDeviceId().getBytes(Charsets.GBK);
            dos.writeByte(bytes.length);
            dos.write(bytes);

            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceInfo{");
        sb.append("issuedAt=").append(issuedAt);
        sb.append(", reserved=").append(reserved);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", mobileNo=").append(mobileNo);
        sb.append(", agencyId=").append(agencyId);
        sb.append(", vehicleId=").append(vehicleId);
        sb.append(", plateColor=").append(plateColor);
        sb.append(", plateNo=").append(plateNo);
        sb.append(", deviceModel=").append(deviceModel);
        sb.append(", protocolVersion=").append(protocolVersion);
        sb.append('}');
        return sb.toString();
    }
}
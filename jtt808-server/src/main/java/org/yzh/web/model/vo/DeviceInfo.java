package org.yzh.web.model.vo;

import io.github.yezhihao.protostar.util.DateTimeUtils.Bcd;
import org.yzh.protocol.commons.Charsets;

import java.io.*;
import java.time.LocalDate;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class DeviceInfo {

    /** 签发日期 */
    protected LocalDate issuedAt;
    /** 预留字段 */
    protected byte reserved;
    /** 设备ID */
    protected String deviceId;
    /** 终端ID */
    protected String clientId;
    /** 机构ID */
    protected int agencyId;
    /** 车辆ID */
    protected int vehicleId;
    /** 车牌颜色 */
    protected byte plateColor;
    /** 车牌号 */
    protected String plateNo;
    /** 协议版本 */
    protected int protocolVersion;

    public DeviceInfo() {
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
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

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public static DeviceInfo formBytes(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             DataInputStream dis = new DataInputStream(bis)) {

            DeviceInfo result = new DeviceInfo();
            byte[] temp;
            dis.read(temp = new byte[3]);
            result.setIssuedAt(Bcd.toDate(temp));
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

            dos.write(Bcd.from(deviceInfo.getIssuedAt()));
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
        sb.append(", deviceId='").append(deviceId).append('\'');
        sb.append(", clientId='").append(clientId).append('\'');
        sb.append(", agencyId=").append(agencyId);
        sb.append(", vehicleId=").append(vehicleId);
        sb.append(", plateColor=").append(plateColor);
        sb.append(", plateNo='").append(plateNo).append('\'');
        sb.append(", protocolVersion=").append(protocolVersion);
        sb.append('}');
        return sb.toString();
    }
}
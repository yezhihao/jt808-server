package org.yzh.web.model.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.*;

public class DeviceInfo {

    /** 签发时间（秒） */
    private int issuedAt;
    /** 有效期 （秒） */
    private int validAt;
    /** 车牌颜色 */
    private byte plateColor;
    /** 车牌号 */
    private String plateNo;
    /** 设备ID */
    private String deviceId;

    public DeviceInfo() {
    }

    public DeviceInfo(String plateNo, String deviceId) {
        this.plateNo = plateNo;
        this.deviceId = deviceId;
    }

    public int getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(int issuedAt) {
        this.issuedAt = issuedAt;
    }

    public int getValidAt() {
        return validAt;
    }

    public void setValidAt(int validAt) {
        this.validAt = validAt;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public static DeviceInfo formBytes(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             DataInputStream dis = new DataInputStream(bis)) {

            DeviceInfo result = new DeviceInfo();
            result.setIssuedAt(dis.readInt());
            result.setValidAt(dis.readInt());
            result.setPlateColor(dis.readByte());
            result.setPlateNo(dis.readUTF());
            result.setDeviceId(dis.readUTF());

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toBytes(DeviceInfo deviceToken) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(32);
             DataOutputStream dos = new DataOutputStream(bos)) {

            dos.writeInt(deviceToken.getIssuedAt());
            dos.writeInt(deviceToken.getValidAt());
            dos.writeByte(deviceToken.getPlateColor());
            dos.writeUTF(deviceToken.getPlateNo());
            dos.writeUTF(deviceToken.getDeviceId());

            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("issuedAt", issuedAt)
                .append("validAt", validAt)
                .append("plateColor", plateColor)
                .append("plateNo", plateNo)
                .append("deviceId", deviceId)
                .toString();
    }
}
package org.yzh.web.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.yzh.protocol.commons.Charsets;

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

    public DeviceInfo() {
    }

    public DeviceInfo(byte[] bytes) {
        formBytes(bytes);
    }

    public DeviceInfo(String deviceId, LocalDate issuedAt) {
        this.deviceId = deviceId;
        this.issuedAt = issuedAt;
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

    public DeviceInfo formBytes(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             DataInputStream dis = new DataInputStream(bis)) {

            byte[] temp;
            dis.read(temp = new byte[3]);
            this.issuedAt = BCD.toDate(temp);
            this.reserved = dis.readByte();
            int len = dis.readUnsignedByte();
            dis.read(temp = new byte[len]);
            this.deviceId = new String(temp, Charsets.GBK);

            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] toBytes() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(32);
             DataOutputStream dos = new DataOutputStream(bos)) {

            dos.write(BCD.from(issuedAt));
            dos.writeByte(reserved);
            byte[] bytes = deviceId.getBytes(Charsets.GBK);
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
        sb.append('}');
        return sb.toString();
    }
}
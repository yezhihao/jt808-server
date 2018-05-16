package org.yzh.web.jt808.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.config.Charsets;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 下发终端升级包
 */
public class TerminalUpgradePack extends PackageData<Header> {

    public static final int Terminal = 0;
    public static final int CardReader = 12;
    public static final int Beidou = 52;

    private Integer type;

    private String manufacturerId;

    private Integer versionLen;

    private String version;

    private Long packetLen;

    private byte[] packet;

    @Property(index = 0, type = DataType.BYTE, desc = "升级类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 1, type = DataType.STRING, length = 5, pad = 32, desc = "制造商ID,终端制造商编码")
    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @JsonIgnore
    @Property(index = 6, type = DataType.BYTE, desc = "版本号长度")
    public Integer getVersionLen() {
        if (versionLen == null)
            this.versionLen = version.getBytes(Charsets.GBK).length;
        return versionLen;
    }

    public void setVersionLen(Integer versionLen) {
        this.versionLen = versionLen;
    }

    @Property(index = 7, type = DataType.STRING, lengthName = "versionLen", desc = "版本号")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
        this.versionLen = version.getBytes(Charsets.GBK).length;
    }

    @JsonIgnore
    @Property(index = 7, indexOffsetName = "versionLen", type = DataType.DWORD, desc = "数据包长度")
    public Long getPacketLen() {
        return packetLen;
    }

    public void setPacketLen(Long packetLen) {
        this.packetLen = packetLen;
    }

    @Property(index = 11, indexOffsetName = "versionLen", type = DataType.BYTES, lengthName = "packetLen", desc = "数据包")
    public byte[] getPacket() {
        return packet;
    }

    public void setPacket(byte[] packet) {
        this.packet = packet;
    }

}
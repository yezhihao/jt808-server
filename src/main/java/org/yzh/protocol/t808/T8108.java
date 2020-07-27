package org.yzh.protocol.t808;

import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.Charsets;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.下发终端升级包)
public class T8108 extends AbstractMessage<Header> {

    public static final int Terminal = 0;
    public static final int CardReader = 12;
    public static final int Beidou = 52;

    private Integer type;

    private String manufacturerId;

    private Integer versionLen;

    private String version;

    private Integer packetLen;

    private byte[] packet;

    @Field(index = 0, type = DataType.BYTE, desc = "升级类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.STRING, length = 5, pad = 32, desc = "制造商ID,终端制造商编码")
    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Field(index = 6, type = DataType.BYTE, desc = "版本号长度")
    public Integer getVersionLen() {
        if (versionLen == null)
            this.versionLen = version.getBytes(Charsets.GBK).length;
        return versionLen;
    }

    public void setVersionLen(Integer versionLen) {
        this.versionLen = versionLen;
    }

    @Field(index = 7, type = DataType.STRING, lengthName = "versionLen", desc = "版本号")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
        this.versionLen = version.getBytes(Charsets.GBK).length;
    }

    @Field(index = 7, indexOffsetName = "versionLen", type = DataType.DWORD, desc = "数据包长度")
    public Integer getPacketLen() {
        return packetLen;
    }

    public void setPacketLen(Integer packetLen) {
        this.packetLen = packetLen;
    }

    @Field(index = 11, indexOffsetName = "versionLen", type = DataType.BYTES, lengthName = "packetLen", desc = "数据包")
    public byte[] getPacket() {
        return packet;
    }

    public void setPacket(byte[] packet) {
        this.packet = packet;
    }
}
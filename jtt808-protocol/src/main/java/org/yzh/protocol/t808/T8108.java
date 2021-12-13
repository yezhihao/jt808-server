package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.下发终端升级包)
public class T8108 extends JTMessage {

    public static final int Terminal = 0;
    public static final int CardReader = 12;
    public static final int Beidou = 52;

    @Field(length = 1, desc = "升级类型")
    private int type;
    @Field(length = 5, desc = "制造商ID,终端制造商编码")
    private String makerId;
    @Field(lengthUnit = 1, desc = "版本号")
    private String version;
    @Field(lengthUnit = 4, desc = "数据包")
    private byte[] packet;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMakerId() {
        return makerId;
    }

    public void setMakerId(String makerId) {
        this.makerId = makerId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public byte[] getPacket() {
        return packet;
    }

    public void setPacket(byte[] packet) {
        this.packet = packet;
    }
}
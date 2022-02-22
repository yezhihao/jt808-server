package org.yzh.protocol.jsatl12;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JSATL12;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JSATL12.报警附件上传指令)
public class T9208 extends JTMessage {

    private static final byte[] RESERVED = new byte[16];

    @Field(lengthUnit = 1, desc = "服务器IP地址")
    private String ip;
    @Field(length = 2, desc = "TCP端口")
    private int tcpPort;
    @Field(length = 2, desc = "UDP端口")
    private int udpPort;
    @Field(length = 16, desc = "报警标识号", version = {-1, 0})
    @Field(length = 40, desc = "报警标识号(粤标)", version = 1)
    private AlarmId alarmId;
    @Field(length = 32, desc = "报警编号")
    private String platformAlarmId;
    @Field(length = 16, desc = "预留")
    private byte[] reserved = RESERVED;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public AlarmId getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(AlarmId alarmId) {
        this.alarmId = alarmId;
    }

    public String getPlatformAlarmId() {
        return platformAlarmId;
    }

    public void setPlatformAlarmId(String platformAlarmId) {
        this.platformAlarmId = platformAlarmId;
    }

    public byte[] getReserved() {
        return reserved;
    }

    public void setReserved(byte[] reserved) {
        this.reserved = reserved;
    }
}
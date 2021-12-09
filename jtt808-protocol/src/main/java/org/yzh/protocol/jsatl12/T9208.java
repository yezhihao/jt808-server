package org.yzh.protocol.jsatl12;

import io.github.yezhihao.protostar.DataType;
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

    @Field(index = 0, type = DataType.STRING, lengthSize = 1, desc = "服务器IP地址")
    private String ip;
    @Field(index = 1, type = DataType.WORD, desc = "TCP端口")
    private int tcpPort;
    @Field(index = 3, type = DataType.WORD, desc = "UDP端口")
    private int udpPort;
    @Field(index = 5, length = 16, type = DataType.OBJ, desc = "报警标识号", version = {-1, 0})
    @Field(index = 5, length = 40, type = DataType.OBJ, desc = "报警标识号(粤标)", version = 1)
    private AlarmId alarmId;
    @Field(index = 21, length = 32, type = DataType.BYTES, desc = "报警编号")
    private String alarmNo;
    @Field(index = 53, length = 16, type = DataType.BYTES, desc = "预留")
    private byte[] reserved;

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

    public String getAlarmNo() {
        return alarmNo;
    }

    public void setAlarmNo(String alarmNo) {
        this.alarmNo = alarmNo;
    }

    public byte[] getReserved() {
        return reserved;
    }

    public void setReserved(byte[] reserved) {
        this.reserved = reserved;
    }
}
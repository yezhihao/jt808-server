package org.yzh.protocol.jsatl12;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.Charsets;
import org.yzh.protocol.commons.JSATL12;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JSATL12.报警附件上传指令)
public class T9208 extends AbstractMessage<Header> {

    private int ipLength;
    private String ip;
    private int tcpPort;
    private int udpPort;
    private AlarmId alarmId;
    private String alarmNo;
    private byte[] reserved;


    @Field(index = 0, type = DataType.BYTE, desc = "IP地址长度")
    public int getIpLength() {
        return ipLength;
    }

    public void setIpLength(int ipLength) {
        this.ipLength = ipLength;
    }

    @Field(index = 1, type = DataType.STRING, lengthName = "ipLength", desc = "服务器IP地址")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
        this.ipLength = ip.getBytes(Charsets.GBK).length;
    }

    @Field(index = 1, indexOffsetName = "ipLength", type = DataType.WORD, desc = "TCP端口")
    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    @Field(index = 3, indexOffsetName = "ipLength", type = DataType.WORD, desc = "UDP端口")
    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    @Field(index = 5, indexOffsetName = "ipLength", length = 16, type = DataType.OBJ, desc = "报警标识号")
    public AlarmId getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(AlarmId alarmId) {
        this.alarmId = alarmId;
    }

    @Field(index = 21, indexOffsetName = "ipLength", length = 32, type = DataType.BYTES, desc = "报警编号")
    public String getAlarmNo() {
        return alarmNo;
    }

    public void setAlarmNo(String alarmNo) {
        this.alarmNo = alarmNo;
    }

    @Field(index = 53, indexOffsetName = "ipLength", length = 16, type = DataType.BYTES, desc = "预留")
    public byte[] getReserved() {
        return reserved;
    }

    public void setReserved(byte[] reserved) {
        this.reserved = reserved;
    }
}
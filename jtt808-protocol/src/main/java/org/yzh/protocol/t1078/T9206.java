package org.yzh.protocol.t1078;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message(JT1078.文件上传指令)
public class T9206 extends JTMessage {

    @Field(index = 0, type = DataType.STRING, lengthSize = 1, desc = "服务器地址")
    private String ip;
    @Field(index = 1, type = DataType.WORD, desc = "端口")
    private int port;
    @Field(index = 3, type = DataType.STRING, lengthSize = 1, desc = "用户名")
    private String username;
    @Field(index = 4, type = DataType.STRING, lengthSize = 1, desc = "密码")
    private String password;
    @Field(index = 5, type = DataType.STRING, lengthSize = 1, desc = "文件上传路径")
    private String path;
    @Field(index = 6, type = DataType.BYTE, desc = "逻辑通道号")
    private int channelNo;
    @Field(index = 7, type = DataType.BCD8421, length = 6, desc = "开始时间(YYMMDDHHMMSS)")
    private String startTime;
    @Field(index = 13, type = DataType.BCD8421, length = 6, desc = "结束时间(YYMMDDHHMMSS)")
    private String endTime;
    @Field(index = 19, type = DataType.DWORD, desc = "报警标志0~31(参考808协议文档报警标志位定义)")
    private int warnBit1;
    @Field(index = 23, type = DataType.DWORD, desc = "报警标志32~63")
    private int warnBit2;
    @Field(index = 27, type = DataType.BYTE, desc = "音视频资源类型：0.音视频 1.音频 2.视频 3.视频或音视频")
    private int mediaType;
    @Field(index = 28, type = DataType.BYTE, desc = "码流类型：0.所有码流 1.主码流 2.子码流")
    private int streamType;
    @Field(index = 29, type = DataType.BYTE, desc = "存储位置：0.所有存储器 1.主存储器 2.灾备存储器")
    private int storageType;
    @Field(index = 30, type = DataType.BYTE, desc = "任务执行条件(用bit位表示)：[0]WIFI下可下载 [1]LAN连接时可下载 [2]3G/4G连接时可下载")
    private int condition;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getWarnBit1() {
        return warnBit1;
    }

    public void setWarnBit1(int warnBit1) {
        this.warnBit1 = warnBit1;
    }

    public int getWarnBit2() {
        return warnBit2;
    }

    public void setWarnBit2(int warnBit2) {
        this.warnBit2 = warnBit2;
    }

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    public int getStorageType() {
        return storageType;
    }

    public void setStorageType(int storageType) {
        this.storageType = storageType;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }
}
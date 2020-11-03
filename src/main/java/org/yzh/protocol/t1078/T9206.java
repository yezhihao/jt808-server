package org.yzh.protocol.t1078;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT1078.文件上传指令)
public class T9206 extends JTMessage {

    private String ip;
    private int port;
    private String username;
    private String password;
    private String path;
    private int channelNo;
    private String startTime;
    private String endTime;
    private byte[] warningMark;
    private int mediaType;
    private int streamType;
    private int memoryType;
    private int condition;

    @Field(index = 1, type = DataType.STRING, lengthSize = 1, desc = "服务器地址")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Field(index = 1, type = DataType.WORD, desc = "端口")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Field(index = 4, type = DataType.STRING, lengthSize = 1, desc = "用户名")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Field(index = 5, type = DataType.STRING, lengthSize = 1, desc = "密码")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Field(index = 6, type = DataType.STRING, lengthSize = 1, desc = "文件上传路径")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Field(index = 6, type = DataType.BYTE, desc = "逻辑通道号")
    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    @Field(index = 7, type = DataType.BCD8421, length = 6, desc = "开始时间（yyMMddHHmmss）")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Field(index = 13, type = DataType.BCD8421, length = 6, desc = "结束时间（yyMMddHHmmss）")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Field(index = 19, type = DataType.BYTES, length = 8, desc = "报警标志")
    public byte[] getWarningMark() {
        return warningMark;
    }

    public void setWarningMark(byte[] warningMark) {
        this.warningMark = warningMark;
    }

    @Field(index = 27, type = DataType.BYTE, desc = "音视频资源类型")
    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    @Field(index = 28, type = DataType.BYTE, desc = "码流类型")
    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    @Field(index = 29, type = DataType.BYTE, desc = "存储位置")
    public int getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(int memoryType) {
        this.memoryType = memoryType;
    }

    @Field(index = 30, type = DataType.BYTE, desc = "任务执行条件")
    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }
}
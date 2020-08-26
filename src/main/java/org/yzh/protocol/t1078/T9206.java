package org.yzh.protocol.t1078;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT1078.文件上传指令)
public class T9206 extends AbstractMessage<Header> {

    private int ipLength;
    private String ip;
    private int port;

    private int usernameLength;
    private String username;

    private int passwordLength;
    private String password;

    private int pathLength;
    private String path;

    private int channelNo;
    private String startTime;
    private String endTime;
    private byte[] warningMark;
    private int dataType;
    private int streamType;
    private int storageType;
    private int condition;

    @Field(index = 0, type = DataType.BYTE, desc = "服务器地址长度")
    public int getIpLength() {
        return ipLength;
    }

    public void setIpLength(int ipLength) {
        this.ipLength = ipLength;
    }

    @Field(index = 0, lengthName = "ipLength", type = DataType.STRING, desc = "服务器地址")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Field(index = 0, indexOffsetName = "ipLength", type = DataType.WORD, desc = "端口")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Field(index = 0, indexOffsetName = "ipLength", type = DataType.BYTE, desc = "用户名长度")
    public int getUsernameLength() {
        return usernameLength;
    }

    public void setUsernameLength(int usernameLength) {
        this.usernameLength = usernameLength;
    }

    @Field(index = 0, indexOffsetName = "ipLength", lengthName = "usernameLength", type = DataType.STRING, desc = "用户名")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Field(index = 0, indexOffsetName = {"ipLength", "usernameLength"}, type = DataType.BYTE, desc = "密码长度")
    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
    }

    @Field(index = 0, indexOffsetName = {"ipLength", "usernameLength"}, lengthName = "passwordLength", type = DataType.STRING, desc = "密码")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Field(index = 0, indexOffsetName = {"ipLength", "usernameLength", "passwordLength"}, type = DataType.BYTE, desc = "文件上传路径长度")
    public int getPathLength() {
        return pathLength;
    }

    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }

    @Field(index = 0, indexOffsetName = {"ipLength", "usernameLength", "passwordLength"}, lengthName = "pathLength", type = DataType.STRING, desc = "文件上传路径")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Field(index = 0, indexOffsetName = {"ipLength", "usernameLength", "passwordLength", "pathLength"}, type = DataType.BYTE, desc = "逻辑通道号")
    public int getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(int channelNo) {
        this.channelNo = channelNo;
    }

    @Field(index = 0, indexOffsetName = {"ipLength", "usernameLength", "passwordLength", "pathLength"}, type = DataType.BCD8421, length = 6, desc = "开始时间（yyMMddHHmmss）")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Field(index = 0, indexOffsetName = {"ipLength", "usernameLength", "passwordLength", "pathLength"}, type = DataType.BCD8421, length = 6, desc = "结束时间（yyMMddHHmmss）")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Field(index = 0, indexOffsetName = {"ipLength", "usernameLength", "passwordLength", "pathLength"}, type = DataType.BYTES, length = 8, desc = "报警标志")
    public byte[] getWarningMark() {
        return warningMark;
    }

    public void setWarningMark(byte[] warningMark) {
        this.warningMark = warningMark;
    }

    @Field(index = 0, indexOffsetName = {"ipLength", "usernameLength", "passwordLength", "pathLength"}, type = DataType.BYTE, desc = "音视频资源类型")
    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    @Field(index = 0, indexOffsetName = {"ipLength", "usernameLength", "passwordLength", "pathLength"}, type = DataType.BYTE, desc = "码流类型")
    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    @Field(index = 0, indexOffsetName = {"ipLength", "usernameLength", "passwordLength", "pathLength"}, type = DataType.BYTE, desc = "存储位置")
    public int getStorageType() {
        return storageType;
    }

    public void setStorageType(int storageType) {
        this.storageType = storageType;
    }


    @Field(index = 0, indexOffsetName = {"ipLength", "usernameLength", "passwordLength", "pathLength"}, type = DataType.BYTE, desc = "任务执行条件")
    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }
}
package org.yzh.protocol;

import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.t1078.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JT/T 1078协议单元测试数据
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JT1078Beans {

    private static final LocalDateTime startTime = LocalDateTime.of(2020, 01, 01, 00, 00, 00);
    private static final LocalDateTime endTime = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

    /** 2013版消息头 */
    public static JTMessage H2013(JTMessage message) {
        Header header = new Header();
        header.setMessageId(message.reflectMessageId());
        header.setMobileNo("12345678901");
        header.setSerialNo((int) Short.MAX_VALUE);
        header.setEncryption(0);
        header.setReserved(false);
        message.setHeader(header);
        return message;
    }

    /** 2019版消息头 */
    public static JTMessage H2019(JTMessage message) {
        Header header = new Header();
        header.setMessageId(message.reflectMessageId());
        header.setVersionNo(1);
        header.setMobileNo("17299841738");
        header.setSerialNo(65535);
        header.setEncryption(0);
        header.setVersion(true);
        header.setReserved(false);
        message.setHeader(header);
        return message;
    }

    //终端上传音视频属性
    public static T1003 T1003() {
        T1003 bean = new T1003();
        bean.setAudioFormat(127);
        bean.setAudioChannels(4);
        bean.setAudioSamplingRate(2);
        bean.setAudioBitDepth(0);
        bean.setAudioFrameLength(37961);
        bean.setAudioSupport(1);
        bean.setVideoFormat(32);
        bean.setMaxAudioChannels(8);
        bean.setMaxVideoChannels(8);
        return bean;
    }

    //终端上传乘客流量
    public static T1005 T1005() {
        T1005 bean = new T1005();
        bean.setStartTime("200707192359");
        bean.setEndTime("200707192359");
        bean.setGetOffCount(18450);
        bean.setGetOnCount(33269);
        return bean;
    }

    //终端上传音视频资源列表
    public static T1205 T1205() {
        byte[] bytes = new byte[8];
        List<T1205.Item> items = new ArrayList<>();
        items.add(new T1205.Item(1, startTime, endTime, bytes, 1, 1, 1, 1024));
        items.add(new T1205.Item(2, startTime, endTime, bytes, 2, 2, 2, 2048));

        T1205 bean = new T1205();
        bean.setResponseSerialNo(4321);
        bean.setCount(items.size());
        bean.setItems(items);
        return bean;
    }

    //文件上传完成通知
    public static T1206 T1206() {
        T1206 bean = new T1206();
        bean.setResponseSerialNo(7050);
        bean.setResult(1);
        return bean;
    }

    //实时音视频传输请求
    public static T9101 T9101() {
        T9101 bean = new T9101();
        bean.setIp("123.123.123.123");
        bean.setTcpPort(772);
        bean.setUdpPort(16582);
        bean.setChannelNo(12);
        bean.setMediaType(1);
        bean.setStreamType(0);
        return bean;
    }

    //音视频实时传输控制
    public static T9102 T9102() {
        T9102 bean = new T9102();
        bean.setChannelNo(8);
        bean.setCommand(1);
        bean.setCloseType(2);
        bean.setStreamType(3);
        return bean;
    }

    //实时音视频传输状态通知
    public static T9105 T9105() {
        T9105 bean = new T9105();
        bean.setChannelNo(2);
        bean.setPacketLossRate(3);
        return bean;
    }

    //平台下发远程录像回放请求
    public static T9201 T9201() {
        T9201 bean = new T9201();
        bean.setIp("12.12.123.123");
        bean.setTcpPort(42937);
        bean.setUdpPort(15468);
        bean.setChannelNo(26674);
        bean.setMediaType(2);
        bean.setStreamType(0);
        bean.setMemoryType(0);
        bean.setPlaybackMode(0);
        bean.setPlaybackSpeed(0);
        bean.setStartTime("200707192359");
        bean.setEndTime("200707192359");
        return bean;
    }

    //平台下发远程录像回放控制
    public static T9202 T9202() {
        T9202 bean = new T9202();
        bean.setChannelNo(14865);
        bean.setPlaybackMode(1);
        bean.setPlaybackSpeed(3);
        bean.setPlaybackTime("200707192359");
        return bean;
    }

    //查询资源列表
    public static T9205 T9205() {
        T9205 bean = new T9205();
        bean.setChannelNo(34023);
        bean.setMediaType(20635);
        bean.setStartTime("200707192359");
        bean.setEndTime("200707192359");
        bean.setWarningMark(new byte[8]);
        bean.setMemoryType(42752);
        bean.setStreamType(40558);
        return bean;
    }

    //文件上传指令
    public static T9206 T9206() {
        T9206 bean = new T9206();
        bean.setIp("192.168.1.1");
        bean.setPort(11053);
        bean.setUsername("username");
        bean.setPassword("password");
        bean.setPath("/alarm_file");
        bean.setChannelNo(1);
        bean.setStartTime("200707192359");
        bean.setEndTime("200707192359");
        bean.setWarningMark(new byte[8]);
        bean.setMediaType(0);
        bean.setMemoryType(1);
        bean.setStreamType(1);
        bean.setCondition(1);
        return bean;
    }

    //文件上传控制
    public static T9207 T9207() {
        T9207 bean = new T9207();
        bean.setResponseSerialNo(27133);
        bean.setCommand(2);
        return bean;
    }

    //云台旋转
    public static T9301 T9301() {
        T9301 bean = new T9301();
        bean.setChannelNo(1);
        bean.setParam1(2);
        bean.setParam2(3);
        return bean;
    }

    //云台调整焦距控制
    public static T9302 T9302() {
        T9302 bean = new T9302();
        bean.setChannelNo(1);
        bean.setParam(255);
        return bean;
    }
}
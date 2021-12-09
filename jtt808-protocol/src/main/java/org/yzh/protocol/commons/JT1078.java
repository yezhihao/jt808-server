package org.yzh.protocol.commons;

/**
 * 中华人民共和国交通运输行业标准
 * 道路运输车辆卫星定位系统视频通信协议
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public interface JT1078 {

    int 终端上传音视频属性 = 0x1003;
    int 终端上传乘客流量 = 0x1005;
    int 终端上传音视频资源列表 = 0x1205;
    int 文件上传完成通知 = 0x1206;
    int 查询终端音视频属性 = 0x9003;
    int 实时音视频传输请求 = 0x9101;
    int 音视频实时传输控制 = 0x9102;
    int 实时音视频传输状态通知 = 0x9105;
    int 平台下发远程录像回放请求 = 0x9201;
    int 平台下发远程录像回放控制 = 0x9202;
    int 查询资源列表 = 0x9205;
    int 文件上传指令 = 0x9206;
    int 文件上传控制 = 0x9207;
    int 云台旋转 = 0x9301;
    int 云台调整焦距控制 = 0x9302;
    int 云台调整光圈控制 = 0x9303;
    int 云台雨刷控制 = 0x9304;
    int 红外补光控制 = 0x9305;
    int 云台变倍控制 = 0x9306;
    int 实时音视频流及透传数据传输 = 0x30316364;
    String 平台手工唤醒请求_短消息 = "WAKEUP";
}
package org.yzh.protocol.commons;

import java.util.HashMap;
import java.util.Map;

import static org.yzh.protocol.commons.JSATL12.*;
import static org.yzh.protocol.commons.JT1078.*;
import static org.yzh.protocol.commons.JT808.*;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class MessageId {

    private static final Map<Integer, String> messageId = new HashMap<>(81);

    public static String get(int id) {
        String name = messageId.get(id);
        if (name != null)
            return name;
        return "0x" + Integer.toHexString(id);
    }

    static {
        messageId.put(终端通用应答, "终端通用应答");
        messageId.put(终端心跳, "终端心跳");
        messageId.put(终端注销, "终端注销");
        messageId.put(查询服务器时间, "查询服务器时间");
        messageId.put(终端补传分包请求, "终端补传分包请求");
        messageId.put(终端注册, "终端注册");
        messageId.put(终端鉴权, "终端鉴权");
        messageId.put(查询终端参数应答, "查询终端参数应答");
        messageId.put(查询终端属性应答, "查询终端属性应答");
        messageId.put(终端升级结果通知, "终端升级结果通知");
        messageId.put(位置信息汇报, "位置信息汇报");
        messageId.put(位置信息查询应答, "位置信息查询应答");
        messageId.put(事件报告, "事件报告");
        messageId.put(提问应答, "提问应答");
        messageId.put(信息点播_取消, "信息点播_取消");
        messageId.put(车辆控制应答, "车辆控制应答");
        messageId.put(查询区域或线路数据应答, "查询区域或线路数据应答");
        messageId.put(行驶记录数据上传, "行驶记录数据上传");
        messageId.put(电子运单上报, "电子运单上报");
        messageId.put(驾驶员身份信息采集上报, "驾驶员身份信息采集上报");
        messageId.put(定位数据批量上传, "定位数据批量上传");
        messageId.put(CAN总线数据上传, "CAN总线数据上传");
        messageId.put(多媒体事件信息上传, "多媒体事件信息上传");
        messageId.put(多媒体数据上传, "多媒体数据上传");
        messageId.put(存储多媒体数据检索应答, "存储多媒体数据检索应答");
        messageId.put(摄像头立即拍摄命令应答, "摄像头立即拍摄命令应答");
        messageId.put(数据上行透传, "数据上行透传");
        messageId.put(数据压缩上报, "数据压缩上报");
        messageId.put(终端RSA公钥, "终端RSA公钥");
        messageId.put(终端上行消息保留, "终端上行消息保留");
        messageId.put(平台通用应答, "平台通用应答");
        messageId.put(服务器补传分包请求, "服务器补传分包请求");
        messageId.put(查询服务器时间应答, "查询服务器时间应答");
        messageId.put(终端注册应答, "终端注册应答");
        messageId.put(设置终端参数, "设置终端参数");
        messageId.put(查询终端参数, "查询终端参数");
        messageId.put(终端控制, "终端控制");
        messageId.put(查询指定终端参数, "查询指定终端参数");
        messageId.put(查询终端属性, "查询终端属性");
        messageId.put(下发终端升级包, "下发终端升级包");
        messageId.put(位置信息查询, "位置信息查询");
        messageId.put(临时位置跟踪控制, "临时位置跟踪控制");
        messageId.put(人工确认报警消息, "人工确认报警消息");
        messageId.put(服务器向终端发起链路检测请求, "服务器向终端发起链路检测请求");
        messageId.put(文本信息下发, "文本信息下发");
        messageId.put(事件设置, "事件设置");
        messageId.put(提问下发, "提问下发");
        messageId.put(信息点播菜单设置, "信息点播菜单设置");
        messageId.put(信息服务, "信息服务");
        messageId.put(电话回拨, "电话回拨");
        messageId.put(设置电话本, "设置电话本");
        messageId.put(车辆控制, "车辆控制");
        messageId.put(设置圆形区域, "设置圆形区域");
        messageId.put(删除圆形区域, "删除圆形区域");
        messageId.put(设置矩形区域, "设置矩形区域");
        messageId.put(删除矩形区域, "删除矩形区域");
        messageId.put(设置多边形区域, "设置多边形区域");
        messageId.put(删除多边形区域, "删除多边形区域");
        messageId.put(设置路线, "设置路线");
        messageId.put(删除路线, "删除路线");
        messageId.put(查询区域或线路数据, "查询区域或线路数据");
        messageId.put(行驶记录仪数据采集命令, "行驶记录仪数据采集命令");
        messageId.put(行驶记录仪参数下传命令, "行驶记录仪参数下传命令");
        messageId.put(上报驾驶员身份信息请求, "上报驾驶员身份信息请求");
        messageId.put(多媒体数据上传应答, "多媒体数据上传应答");
        messageId.put(摄像头立即拍摄命令, "摄像头立即拍摄命令");
        messageId.put(存储多媒体数据检索, "存储多媒体数据检索");
        messageId.put(存储多媒体数据上传, "存储多媒体数据上传");
        messageId.put(录音开始命令, "录音开始命令");
        messageId.put(单条存储多媒体数据检索上传命令, "单条存储多媒体数据检索上传命令");
        messageId.put(数据下行透传, "数据下行透传");
        messageId.put(平台RSA公钥, "平台RSA公钥");
        //============================================ 808End
        messageId.put(终端上传音视频属性, "终端上传音视频属性");
        messageId.put(终端上传乘客流量, "终端上传乘客流量");
        messageId.put(终端上传音视频资源列表, "终端上传音视频资源列表");
        messageId.put(文件上传完成通知, "文件上传完成通知");
        messageId.put(查询终端音视频属性, "查询终端音视频属性");
        messageId.put(实时音视频传输请求, "实时音视频传输请求");
        messageId.put(音视频实时传输控制, "音视频实时传输控制");
        messageId.put(实时音视频传输状态通知, "实时音视频传输状态通知");
        messageId.put(平台下发远程录像回放请求, "平台下发远程录像回放请求");
        messageId.put(平台下发远程录像回放控制, "平台下发远程录像回放控制");
        messageId.put(查询资源列表, "查询资源列表");
        messageId.put(文件上传指令, "文件上传指令");
        messageId.put(文件上传控制, "文件上传控制");
        messageId.put(云台旋转, "云台旋转");
        messageId.put(云台调整焦距控制, "云台调整焦距控制");
        messageId.put(云台调整光圈控制, "云台调整光圈控制");
        messageId.put(云台雨刷控制, "云台雨刷控制");
        messageId.put(红外补光控制, "红外补光控制");
        messageId.put(云台变倍控制, "云台变倍控制");
        messageId.put(实时音视频流及透传数据传输, "实时音视频流及透传数据传输");
        //============================================ 1078End
        messageId.put(报警附件上传指令, "报警附件上传指令");
        messageId.put(文件上传完成消息应答, "文件上传完成消息应答");
        messageId.put(报警附件信息消息, "报警附件信息消息");
        messageId.put(文件信息上传, "文件信息上传");
        messageId.put(文件上传完成消息, "文件上传完成消息");
        messageId.put(文件数据上传, "文件数据上传");
        //============================================ JSATL12End
    }
}
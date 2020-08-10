package org.yzh.protocol.commons;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class JT808 {

    public static final int 终端通用应答 = 0x0001;
    public static final int 终端心跳 = 0x0002;
    public static final int 终端注销 = 0x0003;
    public static final int 查询服务器时间 = 0x0004;//2019 new
    public static final int 终端补传分包请求 = 0x0005;//2019 new
    public static final int 终端注册 = 0x0100;
    public static final int 终端鉴权 = 0x0102;//2019 modify
    public static final int 查询终端参数应答 = 0x0104;
    public static final int 查询终端属性应答 = 0x0107;
    public static final int 终端升级结果通知 = 0x0108;
    public static final int 位置信息汇报 = 0x0200;
    public static final int 位置信息查询应答 = 0x0201;
    public static final int 事件报告 = 0x0301;//2019 del
    public static final int 提问应答 = 0x0302;//2019 del
    public static final int 信息点播_取消 = 0x0303;//2019 del
    public static final int 车辆控制应答 = 0x0500;
    public static final int 查询区域或线路数据应答 = 0x0608;//2019 new
    public static final int 行驶记录数据上传 = 0x0700;
    public static final int 电子运单上报 = 0x0701;
    public static final int 驾驶员身份信息采集上报 = 0x0702;//2019 modify
    public static final int 定位数据批量上传 = 0x0704;
    public static final int CAN总线数据上传 = 0x0705;
    public static final int 多媒体事件信息上传 = 0x0800;
    public static final int 多媒体数据上传 = 0x0801;
    public static final int 存储多媒体数据检索应答 = 0x0802;
    public static final int 摄像头立即拍摄命令应答 = 0x0805;
    public static final int 数据上行透传 = 0x0900;
    public static final int 数据压缩上报 = 0x0901;
    public static final int 终端RSA公钥 = 0x0A00;

    public static final int 终端上行消息保留 = 0x0F00 - 0x0FFF;

    public static final int 平台通用应答 = 0x8001;
    public static final int 服务器补传分包请求 = 0x8003;
    public static final int 查询服务器时间应答 = 0x8004;//2019 new
    public static final int 终端注册应答 = 0x8100;

    public static final int 设置终端参数 = 0x8103;
    public static final int 查询终端参数 = 0x8104;
    public static final int 终端控制 = 0x8105;
    public static final int 查询指定终端参数 = 0x8106;
    public static final int 查询终端属性 = 0x8107;
    public static final int 下发终端升级包 = 0x8108;
    public static final int 位置信息查询 = 0x8201;
    public static final int 临时位置跟踪控制 = 0x8202;
    public static final int 人工确认报警消息 = 0x8203;
    public static final int 服务器向终端发起链路检测请求 = 0x8204;//2019 new
    public static final int 文本信息下发 = 0x8300;//2019 modify
    public static final int 事件设置 = 0x8301;//2019 del
    public static final int 提问下发 = 0x8302;//2019 del
    public static final int 信息点播菜单设置 = 0x8303;//2019 del
    public static final int 信息服务 = 0x8304;//2019 del
    public static final int 电话回拨 = 0x8400;
    public static final int 设置电话本 = 0x8401;
    public static final int 车辆控制 = 0x8500;
    public static final int 设置圆形区域 = 0x8600;//2019 modify
    public static final int 删除圆形区域 = 0x8601;
    public static final int 设置矩形区域 = 0x8602;//2019 modify
    public static final int 删除矩形区域 = 0x8603;
    public static final int 设置多边形区域 = 0x8604;//2019 modify
    public static final int 删除多边形区域 = 0x8605;
    public static final int 设置路线 = 0x8606;
    public static final int 删除路线 = 0x8607;
    public static final int 查询区域或线路数据 = 0x8608;//2019 new
    public static final int 行驶记录仪数据采集命令 = 0x8700;
    public static final int 行驶记录仪参数下传命令 = 0x8701;
    public static final int 上报驾驶员身份信息请求 = 0x8702;

    public static final int 多媒体数据上传应答 = 0x8800;

    public static final int 摄像头立即拍摄命令 = 0x8801;
    public static final int 存储多媒体数据检索 = 0x8802;
    public static final int 存储多媒体数据上传 = 0x8803;
    public static final int 录音开始命令 = 0x8804;
    public static final int 单条存储多媒体数据检索上传命令 = 0x8805;
    public static final int 数据下行透传 = 0x8900;
    public static final int 平台RSA公钥 = 0x8A00;

    public static final int 平台下行消息保留 = 0x8F00 - 0x8FFF;
    public static final int 厂商自定义上行消息 = 0xE000 - 0xEFFF;//2019 new
    public static final int 商自定义下行消息 = 0xF000 - 0xFFFF;//2019 new

    private static final Map<Integer, String> messageId = new HashMap<>();

    public static String get(int id) {
        String name = messageId.get(id);
        if (name != null)
            return name;
        return Integer.toHexString(id);
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
    }
}
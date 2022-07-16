package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.core.annotation.Async;
import io.github.yezhihao.netmc.core.annotation.AsyncBatch;
import io.github.yezhihao.netmc.core.annotation.Endpoint;
import io.github.yezhihao.netmc.core.annotation.Mapping;
import io.github.yezhihao.netmc.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.*;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.service.FileService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.yzh.protocol.commons.JT808.*;

@Endpoint
@Component
public class JT808Endpoint {

    private static final Logger log = LoggerFactory.getLogger(JT808Endpoint.class.getSimpleName());

    @Autowired
    private FileService fileService;

    @Mapping(types = 终端通用应答, desc = "终端通用应答")
    public Object generalResponse(T0001 message, Session session) {
        session.response(message);
        return null;
    }

    @Mapping(types = 终端心跳, desc = "终端心跳")
    public void heartbeat(JTMessage message, Session session) {
    }

    @Mapping(types = 终端注销, desc = "终端注销")
    public void unregister(JTMessage message, Session session) {
        session.invalidate();
    }

    @Mapping(types = 查询服务器时间, desc = "查询服务器时间")
    public T8004 serverTime(JTMessage message, Session session) {
        T8004 result = new T8004(LocalDateTime.now(ZoneOffset.UTC));
        return result;
    }

    @Mapping(types = 终端补传分包请求, desc = "终端补传分包请求")
    public void retransmissionPacket(T8003 message, Session session) {
    }

    @Mapping(types = 终端注册, desc = "终端注册")
    public T8100 register(T0100 message, Session session) {
        session.register(message);
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDeviceId(message.getDeviceId());
        deviceInfo.setMobileNo(message.getClientId());
        deviceInfo.setPlateNo(message.getPlateNo());
        session.setAttribute(SessionKey.DeviceInfo, deviceInfo);

        T8100 result = new T8100();
        result.setResponseSerialNo(message.getSerialNo());
        result.setToken(message.getDeviceId() + "," + message.getPlateNo());
        result.setResultCode(T8100.Success);
        return result;
    }

    @Mapping(types = 终端鉴权, desc = "终端鉴权")
    public T0001 authentication(T0102 message, Session session) {
        session.register(message);
        DeviceInfo deviceInfo = new DeviceInfo();
        String[] token = message.getToken().split(",");
        deviceInfo.setMobileNo(message.getClientId());
        deviceInfo.setDeviceId(token[0]);
        if (token.length > 1)
            deviceInfo.setPlateNo(token[1]);
        session.setAttribute(SessionKey.DeviceInfo, deviceInfo);

        T0001 result = new T0001();
        result.setResponseSerialNo(message.getSerialNo());
        result.setResponseMessageId(message.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 查询终端参数应答, desc = "查询终端参数应答")
    public void parametersResponse(T0104 message, Session session) {
        session.response(message);
    }

    @Mapping(types = 查询终端属性应答, desc = "查询终端属性应答")
    public void attributesResponse(T0107 message, Session session) {
        session.response(message);
    }

    @Mapping(types = 终端升级结果通知, desc = "终端升级结果通知")
    public void upgradeResponse(T0108 message, Session session) {
    }

    /**
     * 异步批量处理
     * poolSize：参考数据库CPU核心数量
     * maxElements：最大累积4000条记录处理一次
     * maxWait：最大等待时间1秒
     */
    @AsyncBatch(poolSize = 2, maxElements = 4000, maxWait = 1000)
    @Mapping(types = 位置信息汇报, desc = "位置信息汇报")
    public void locationReport(List<T0200> list) {
    }

    @Mapping(types = 定位数据批量上传, desc = "定位数据批量上传")
    public void locationBatchReport(T0704 message) {
    }

    @Mapping(types = {位置信息查询应答, 车辆控制应答}, desc = "位置信息查询应答/车辆控制应答")
    public void locationResponse(T0201_0500 message, Session session) {
        session.response(message);
    }

    @Mapping(types = 事件报告, desc = "事件报告")
    public void eventReport(T0301 message, Session session) {
    }

    @Mapping(types = 提问应答, desc = "提问应答")
    public void questionResponse(T0302 message, Session session) {
    }

    @Mapping(types = 信息点播_取消, desc = "信息点播/取消")
    public void newsCancel(T0303 message, Session session) {
    }

    @Mapping(types = 查询区域或线路数据应答, desc = "查询区域或线路数据应答")
    public void areaLocationResponse(T0608 message, Session session) {
        session.response(message);
    }

    @Mapping(types = 行驶记录数据上传, desc = "行驶记录仪数据上传")
    public void driveRecorderResponse(T0700 message, Session session) {
        session.response(message);
    }

    @Mapping(types = 电子运单上报, desc = "电子运单上报")
    public void ewaybillReport(JTMessage message, Session session) {
    }

    @Mapping(types = 驾驶员身份信息采集上报, desc = "驾驶员身份信息采集上报")
    public void driverInfoResponse(T0702 message, Session session) {
        session.response(message);
    }

    @Mapping(types = CAN总线数据上传, desc = "CAN总线数据上传")
    public void canBusDataReport(T0705 message, Session session) {
    }

    @Mapping(types = 多媒体事件信息上传, desc = "多媒体事件信息上传")
    public void mediaEventReport(T0800 message, Session session) {
    }

    @Async
    @Mapping(types = 多媒体数据上传, desc = "多媒体数据上传")
    public JTMessage mediaUploadResponse(T0801 message, Session session) {
        if (message.getPacket() == null) {
            T0001 result = new T0001();
            result.copyBy(message);
            result.setMessageId(JT808.平台通用应答);
            result.setSerialNo(session.nextSerialNo());

            result.setResponseSerialNo(message.getSerialNo());
            result.setResponseMessageId(message.getMessageId());
            result.setResultCode(T0001.Success);
            return result;
        }
        fileService.saveMediaFile(message);
        T8800 result = new T8800();
        result.setMediaId(message.getId());
        return result;
    }

    @Mapping(types = 存储多媒体数据检索应答, desc = "存储多媒体数据检索应答")
    public void mediaSearchResponse(T0802 message, Session session) {
        session.response(message);
    }

    @Mapping(types = 摄像头立即拍摄命令应答, desc = "摄像头立即拍摄命令应答")
    public void mediaVideoRecordResponse(T0805 message, Session session) {
        session.response(message);
    }

    @Mapping(types = 数据上行透传, desc = "数据上行透传")
    public void passthrough(T0900 message, Session session) {
    }

    @Mapping(types = 数据压缩上报, desc = "数据压缩上报")
    public void gzipPackReport(T0901 message, Session session) {
    }

    @Mapping(types = 终端RSA公钥, desc = "终端RSA公钥")
    public void rsaSwap(T0A00_8A00 message, Session session) {
        session.response(message);
    }
}
package org.yzh.web.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.framework.mvc.annotation.AsyncBatch;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.session.MessageManager;
import org.yzh.framework.session.Session;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.t808.*;
import org.yzh.web.commons.DateUtils;
import org.yzh.web.commons.EncryptUtils;
import org.yzh.web.model.vo.DeviceInfo;
import org.yzh.web.service.DeviceService;
import org.yzh.web.service.LocationService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static org.yzh.protocol.commons.JT808.*;

@Endpoint
@Component
public class JT808Endpoint {

    private static final Logger log = LoggerFactory.getLogger(JT808Endpoint.class.getSimpleName());

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DeviceService deviceService;

    @Mapping(types = 终端通用应答, desc = "终端通用应答")
    public Object 终端通用应答(T0001 message) {
        messageManager.response(message);
        return null;
    }

    @Mapping(types = 终端心跳, desc = "终端心跳")
    public Object heartBeat(Header header, Session session) {
        return null;
    }

    @Mapping(types = 终端注销, desc = "终端注销")
    public void 终端注销(Header header, Session session) {
        session.invalidate();
    }

    @Mapping(types = 查询服务器时间, desc = "查询服务器时间")
    public T8004 查询服务器时间(Header header, Session session) {
        T8004 result = new T8004(DateUtils.yyMMddHHmmss.format(new Date(System.currentTimeMillis() + 50)));
        result.setHeader(new Header(查询服务器时间应答, session.nextSerialNo(), header.getClientId()));
        return result;
    }

    @Mapping(types = 终端补传分包请求, desc = "终端补传分包请求")
    public void 终端补传分包请求(T8003 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 终端注册, desc = "终端注册")
    public T8100 register(T0100 message, Session session) {
        Header header = message.getHeader();
        if (message.getPlateNo() == null) {
            session.recordProtocolVersion(header.getClientId(), -1);
            log.warn(">>>>>>>>>>可能为2011版本协议，将在下次请求时尝试解析{},{}", session, message);
            return null;
        } else {
            session.setProtocolVersion(header.getVersionNo());
        }

        T8100 result = new T8100(session.nextSerialNo(), header.getMobileNo());
        result.setSerialNo(header.getSerialNo());

        DeviceInfo device = deviceService.register(message);
        if (device != null) {
            session.register(header, device);

            byte[] bytes = DeviceInfo.toBytes(device);
            bytes = EncryptUtils.encrypt(bytes);
            String token = Base64.getEncoder().encodeToString(bytes);

            result.setResultCode(T8100.Success);
            result.setToken(token);
        } else {
            result.setResultCode(T8100.NotFoundTerminal);
        }
        return result;
    }

    @Mapping(types = 终端鉴权, desc = "终端鉴权")
    public T0001 authentication(T0102 request, Session session) {
        Header header = request.getHeader();

        T0001 result = new T0001(session.nextSerialNo(), header.getMobileNo());
        result.setSerialNo(header.getSerialNo());
        result.setReplyId(header.getMessageId());

        DeviceInfo device = deviceService.authentication(request);
        if (device != null) {
            session.register(header, device);
            result.setResultCode(T0001.Success);
            return result;
        }
        log.warn("终端鉴权失败，{}{}", session, request);
        result.setResultCode(T0001.Failure);
        return result;
    }

    @Mapping(types = 查询终端参数应答, desc = "查询终端参数应答")
    public void 查询终端参数应答(T0104 message) {
        Header header = message.getHeader();
        String mobileNo = header.getMobileNo();
        Integer replyId = header.getSerialNo();
        messageManager.response(message);
    }

    @Mapping(types = 查询终端属性应答, desc = "查询终端属性应答")
    public void 查询终端属性应答(T0107 message) {
        Header header = message.getHeader();
        String mobileNo = header.getMobileNo();
        messageManager.response(message);
    }

    @Mapping(types = 终端升级结果通知, desc = "终端升级结果通知")
    public void 终端升级结果通知(T0108 message, Session session) {
        Header header = message.getHeader();
    }

    //异步批量处理默认 4线程 最大累积100条记录处理一次 最大等待时间1秒
    @AsyncBatch
    @Mapping(types = 位置信息汇报, desc = "位置信息汇报")
    public void 位置信息汇报(List<T0200> list) {
        locationService.batchInsert(list);
    }

    @Mapping(types = 定位数据批量上传, desc = "定位数据批量上传")
    public void 定位数据批量上传(T0704 message) {
        Header header = message.getHeader();
        List<T0704.Item> items = message.getItems();
        List<T0200> list = new ArrayList<>(items.size());
        for (T0704.Item item : items) {
            T0200 position = item.getPosition();
            position.setHeader(header);
            list.add(position);
        }
        locationService.batchInsert(list);
    }

    @Mapping(types = {位置信息查询应答, 车辆控制应答}, desc = "位置信息查询应答/车辆控制应答")
    public void 位置信息查询应答(T0201_0500 message) {
        Header header = message.getHeader();
        String mobileNo = header.getMobileNo();
        Integer replyId = header.getSerialNo();
        messageManager.response(message);
    }

    @Mapping(types = 事件报告, desc = "事件报告")
    public void 事件报告(T0301 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 提问应答, desc = "提问应答")
    public void 提问应答(T0302 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 信息点播_取消, desc = "信息点播/取消")
    public void 信息点播取消(T0303 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 查询区域或线路数据应答, desc = "查询区域或线路数据应答")
    public void 查询区域或线路数据应答(JTMessage message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 行驶记录数据上传, desc = "行驶记录仪数据上传")
    public void 行驶记录仪数据上传(T0700 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 电子运单上报, desc = "电子运单上报")
    public void 电子运单上报(JTMessage message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 驾驶员身份信息采集上报, desc = "驾驶员身份信息采集上报")
    public void 驾驶员身份信息采集上报(T0702 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = CAN总线数据上传, desc = "CAN总线数据上传")
    public void CAN总线数据上传(T0705 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 多媒体事件信息上传, desc = "多媒体事件信息上传")
    public void 多媒体事件信息上传(T0800 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 多媒体数据上传, desc = "多媒体数据上传")
    public T8800 多媒体数据上传(T0801 message, Session session) throws IOException {
        Header header = message.getHeader();
        byte[] packet = message.getPacket();
        FileOutputStream fos = new FileOutputStream("D://test.jpg");
        fos.write(packet);
        fos.close();
        T8800 result = new T8800();
        result.setHeader(new Header(平台通用应答, session.nextSerialNo(), header.getMobileNo()));
        result.setMediaId(1);
        return result;
    }

    @Mapping(types = 存储多媒体数据检索应答, desc = "存储多媒体数据检索应答")
    public void 存储多媒体数据检索应答(T0802 message, Session session) {
        Header header = message.getHeader();
        String mobileNo = header.getMobileNo();
        Integer replyId = header.getSerialNo();
        messageManager.response(message);
    }

    @Mapping(types = 摄像头立即拍摄命令应答, desc = "摄像头立即拍摄命令应答")
    public void 摄像头立即拍摄命令应答(T0805 message) {
        Header header = message.getHeader();
        String mobileNo = header.getMobileNo();
        Integer replyId = header.getSerialNo();
        messageManager.response(message);
    }

    @Mapping(types = 数据上行透传, desc = "数据上行透传")
    public void passthrough(T8900_0900 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 数据压缩上报, desc = "数据压缩上报")
    public void gzipPack(T0901 message, Session session) {
        Header header = message.getHeader();
    }

    @Mapping(types = 终端RSA公钥, desc = "终端RSA公钥")
    public void 终端RSA公钥(T0A00_8A00 message) {
        Header header = message.getHeader();
        String mobileNo = header.getMobileNo();
        messageManager.response(message);
    }
}
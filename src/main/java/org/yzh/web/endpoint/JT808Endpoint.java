package org.yzh.web.endpoint;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yzh.framework.annotation.Endpoint;
import org.yzh.framework.annotation.Mapping;
import org.yzh.framework.message.SyncFuture;
import org.yzh.framework.session.MessageManager;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;
import org.yzh.web.jt808.dto.*;
import org.yzh.web.jt808.dto.basics.Message;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.yzh.web.jt808.common.MessageId.*;

@Endpoint
@Component
public class JT808Endpoint {

    private static final Logger log = LoggerFactory.getLogger(JT808Endpoint.class.getSimpleName());

    private SessionManager sessionManager = SessionManager.getInstance();

    private MessageManager messageManager = MessageManager.INSTANCE;

    //TODO Test
    public Object send(String mobileNo, String hexMessage) {

        if (!hexMessage.startsWith("7e"))
            hexMessage = "7e" + hexMessage + "7e";
        ByteBuf msg = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hexMessage));
        Session session = SessionManager.getInstance().getByMobileNo(mobileNo);


        session.getChannel().writeAndFlush(msg);

        String key = mobileNo;
        SyncFuture receive = messageManager.receive(key);
        try {
            return receive.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            messageManager.remove(key);
            e.printStackTrace();
        }
        return null;
    }

    public Object send(Message message) {
        return send(message, true);
    }

    public Object send(Message message, boolean hasReplyFlowIdId) {
        String mobileNo = message.getMobileNo();
        Session session = sessionManager.getByMobileNo(mobileNo);
        message.setSerialNo(session.currentFlowId());

        session.getChannel().writeAndFlush(message);

        String key = mobileNo + (hasReplyFlowIdId ? message.getSerialNo() : "");
        SyncFuture receive = messageManager.receive(key);
        try {
            return receive.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            messageManager.remove(key);
            e.printStackTrace();
        }
        return null;
    }


    @Mapping(types = 终端通用应答, desc = "终端通用应答")
    public void 终端通用应答(Message<T0001> message) {
        T0001 body = message.getBody();
        String mobileNo = message.getMobileNo();
        Integer replyId = body.getReplyId();
        messageManager.put(mobileNo + replyId, message);
    }

    @Mapping(types = 查询终端参数应答, desc = "查询终端参数应答")
    public void 查询终端参数应答(Message<T0104> message) {
        T0104 body = message.getBody();
        String mobileNo = message.getMobileNo();
        Integer replyId = message.getSerialNo();
        messageManager.put(mobileNo + replyId, message);
    }

    @Mapping(types = 查询终端属性应答, desc = "查询终端属性应答")
    public void 查询终端属性应答(Message<T0107> message) {
        T0107 body = message.getBody();
        String mobileNo = message.getMobileNo();
        messageManager.put(mobileNo, message);
    }

    @Mapping(types = {位置信息查询应答, 车辆控制应答}, desc = "位置信息查询应答/车辆控制应答")
    public void 位置信息查询应答(Message<T0201_0500> message) {
        T0201_0500 body = message.getBody();
        String mobileNo = message.getMobileNo();
        Integer replyId = message.getSerialNo();
        messageManager.put(mobileNo + replyId, message);
    }

    @Mapping(types = 终端RSA公钥, desc = "终端RSA公钥")
    public void 终端RSA公钥(Message<T0A00_8A00> message) {
        T0A00_8A00 body = message.getBody();
        String mobileNo = message.getMobileNo();
        messageManager.put(mobileNo, message);
    }

    @Mapping(types = 摄像头立即拍摄命令应答, desc = "摄像头立即拍摄命令应答")
    public void 摄像头立即拍摄命令应答(Message<T0805> message) {
        T0805 body = message.getBody();
        String mobileNo = message.getMobileNo();
        Integer replyId = message.getSerialNo();
        messageManager.put(mobileNo + replyId, message);
    }

    @Mapping(types = 存储多媒体数据检索应答, desc = "存储多媒体数据检索应答")
    public void 存储多媒体数据检索应答(Message<T0802> message, Session session) {
        T0802 body = message.getBody();
        String mobileNo = message.getMobileNo();
        Integer replyId = message.getSerialNo();
        messageManager.put(mobileNo + replyId, message);
    }
    //=============================================================

    @Mapping(types = 终端心跳, desc = "终端心跳")
    public Message heartBeat(Message message, Session session) {
        T0001 result = new T0001(终端心跳, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 补传分包请求, desc = "补传分包请求")
    public Message 补传分包请求(Message<T8003> message, Session session) {
        T8003 body = message.getBody();
        //TODO
        T0001 result = new T0001(补传分包请求, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 终端注册, desc = "终端注册")
    public Message<T8100> register(Message<T0100> message, Session session) {
        T0100 body = message.getBody();
        //TODO
        sessionManager.put(Session.buildId(session.getChannel()), session);

        T8100 result = new T8100(message.getSerialNo(), T8100.Success, "test_token");
        return new Message(终端注册应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 终端注销, desc = "终端注销")
    public Message 终端注销(Message message, Session session) {
        //TODO
        T0001 result = new T0001(终端注销, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 终端鉴权, desc = "终端鉴权")
    public Message authentication(Message<T0102> message, Session session) {
        T0102 body = message.getBody();
        //TODO
        session.setTerminalId(message.getMobileNo());
        sessionManager.put(Session.buildId(session.getChannel()), session);
        T0001 result = new T0001(终端鉴权, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 终端升级结果通知, desc = "终端升级结果通知")
    public Message 终端升级结果通知(Message<T0108> message, Session session) {
        T0108 body = message.getBody();
        //TODO
        T0001 result = new T0001(终端升级结果通知, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 位置信息汇报, desc = "位置信息汇报")
    public Message 位置信息汇报(Message<T0200> message, Session session) {
        T0200 body = message.getBody();
        //TODO
        T0001 result = new T0001(位置信息汇报, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 人工确认报警消息, desc = "人工确认报警消息")
    public Message 人工确认报警消息(Message<T8203> message, Session session) {
        T8203 body = message.getBody();
        //TODO
        T0001 result = new T0001(位置信息汇报, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 事件报告, desc = "事件报告")
    public Message 事件报告(Message<T0301> message, Session session) {
        T0301 body = message.getBody();
        //TODO
        T0001 result = new T0001(事件报告, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 提问应答, desc = "提问应答")
    public Message 提问应答(Message<T0302> message, Session session) {
        T0302 body = message.getBody();
        //TODO
        T0001 result = new T0001(提问应答, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 信息点播_取消, desc = "信息点播/取消")
    public Message 信息点播取消(Message<T0303> message, Session session) {
        T0303 body = message.getBody();
        //TODO
        T0001 result = new T0001(信息点播_取消, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 电话回拨, desc = "电话回拨")
    public Message 电话回拨(Message<T8400> message, Session session) {
        T8400 body = message.getBody();
        //TODO
        T0001 result = new T0001(电话回拨, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 行驶记录仪数据上传, desc = "行驶记录仪数据上传")
    public Message 行驶记录仪数据上传(Message message, Session session) {
        T0001 result = new T0001(行驶记录仪数据上传, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 行驶记录仪参数下传命令, desc = "行驶记录仪参数下达命令")
    public Message 行驶记录仪参数下传命令(Message message, Session session) {
        //TODO
        T0001 result = new T0001(行驶记录仪参数下传命令, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 电子运单上报, desc = "电子运单上报")
    public Message 电子运单上报(Message message, Session session) {
        //TODO
        T0001 result = new T0001(电子运单上报, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 驾驶员身份信息采集上报, desc = "驾驶员身份信息采集上报")
    public Message 驾驶员身份信息采集上报(Message<T0702> message, Session session) {
        T0702 body = message.getBody();
        //TODO
        T0001 result = new T0001(驾驶员身份信息采集上报, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 定位数据批量上传, desc = "定位数据批量上传")
    public Message 定位数据批量上传(Message<T0704> message, Session session) {
        T0704 body = message.getBody();
        //TODO
        T0001 result = new T0001(定位数据批量上传, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = CAN总线数据上传, desc = "定位数据批量上传")
    public Message CAN总线数据上传(Message<T0705> message, Session session) {
        T0705 body = message.getBody();
        //TODO
        T0001 result = new T0001(CAN总线数据上传, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 多媒体事件信息上传, desc = "多媒体事件信息上传")
    public Message 多媒体事件信息上传(Message<T0800> message, Session session) {
        T0800 body = message.getBody();
        //TODO
        T0001 result = new T0001(多媒体事件信息上传, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 多媒体数据上传, desc = "多媒体数据上传")
    public Message 多媒体数据上传(Message<T0801> message, Session session) throws IOException {
        T0801 body = message.getBody();

        byte[] packet = body.getPacket();
        FileOutputStream fos = new FileOutputStream("D://test.jpg");
        fos.write(packet);
        fos.close();

        T8800 result = new T8800();
        result.setMediaId(body.getId());
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 数据上行透传, desc = "数据上行透传")
    public Message passthrough(Message<T8900_0900> message, Session session) {
        T8900_0900 body = message.getBody();
        //TODO
        T0001 result = new T0001(数据上行透传, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

    @Mapping(types = 数据压缩上报, desc = "数据压缩上报")
    public Message gzipPack(Message<T0901> message, Session session) {
        T0901 body = message.getBody();
        //TODO
        T0001 result = new T0001(数据压缩上报, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }

}
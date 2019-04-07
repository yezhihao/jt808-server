package org.yzh.web.endpoint;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.framework.annotation.Endpoint;
import org.yzh.framework.annotation.Mapping;
import org.yzh.framework.codec.MessageEncoder;
import org.yzh.framework.message.PackageData;
import org.yzh.framework.message.SyncFuture;
import org.yzh.framework.session.MessageManager;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;
import org.yzh.web.jt808.dto.*;
import org.yzh.web.jt808.dto.basics.Header;

import java.util.concurrent.TimeUnit;

import static org.yzh.web.jt808.common.MessageId.*;

@Endpoint
@Component
public class JT808Endpoint {

    private static final Logger logger = LoggerFactory.getLogger(JT808Endpoint.class.getSimpleName());

    private SessionManager sessionManager = SessionManager.getInstance();

    private MessageManager messageManager = MessageManager.INSTANCE;

    @Autowired
    private MessageEncoder encoder;

    //TODO Test
    public Object send(String mobileNumber, String hexMessage) {

        if (!hexMessage.startsWith("7e"))
            hexMessage = "7e" + hexMessage + "7e";
        ByteBuf msg = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hexMessage));
        Session session = SessionManager.getInstance().getByMobileNumber(mobileNumber);


        session.getChannel().writeAndFlush(msg);

        String key = mobileNumber;
        SyncFuture receive = messageManager.receive(key);
        try {
            return receive.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            messageManager.remove(key);
            e.printStackTrace();
        }
        return null;
    }

    public Object send(PackageData<Header> packageData) {
        return send(packageData, true);
    }

    public Object send(PackageData<Header> packageData, boolean hasReplyFlowIdId) {
        Header header = packageData.getHeader();
        String mobileNumber = header.getMobileNumber();
        Session session = sessionManager.getByMobileNumber(mobileNumber);
        header.setSerialNumber(session.currentFlowId());

        ByteBuf buf = encoder.encodeAll(packageData);
        logger.info("{}out,hex:{}\n{}", header.getType(), ByteBufUtil.hexDump(buf), packageData);
        ByteBuf allResultBuf = Unpooled.wrappedBuffer(Unpooled.wrappedBuffer(new byte[]{0x7e}), buf, Unpooled.wrappedBuffer(new byte[]{0x7e}));
        session.getChannel().writeAndFlush(allResultBuf);

        String key = mobileNumber + (hasReplyFlowIdId ? header.getSerialNumber() : "");
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
    public void 终端通用应答(CommonResult packageData) {
        Header header = packageData.getHeader();
        String mobileNumber = header.getMobileNumber();
        Integer replyId = packageData.getFlowId();
        messageManager.put(mobileNumber + replyId, packageData);
    }

    @Mapping(types = 查询终端参数应答, desc = "查询终端参数应答")
    public void 查询终端参数应答(ParameterSettingReply packageData) {
        Header header = packageData.getHeader();
        String mobileNumber = header.getMobileNumber();
        Integer replyId = packageData.getSerialNumber();
        messageManager.put(mobileNumber + replyId, packageData);
    }

    @Mapping(types = 查询终端属性应答, desc = "查询终端属性应答")
    public void 查询终端属性应答(TerminalAttributeReply packageData) {
        Header header = packageData.getHeader();
        String mobileNumber = header.getMobileNumber();
        messageManager.put(mobileNumber, packageData);
    }

    @Mapping(types = {位置信息查询应答, 车辆控制应答}, desc = "位置信息查询应答/车辆控制应答")
    public void 位置信息查询应答(PositionReply packageData) {
        Header header = packageData.getHeader();
        String mobileNumber = header.getMobileNumber();
        Integer replyId = packageData.getSerialNumber();
        messageManager.put(mobileNumber + replyId, packageData);
    }

    @Mapping(types = 终端RSA公钥, desc = "终端RSA公钥")
    public void 终端RSA公钥(RSAPack packageData) {
        Header header = packageData.getHeader();
        String mobileNumber = header.getMobileNumber();
        messageManager.put(mobileNumber, packageData);
    }

    @Mapping(types = 摄像头立即拍摄命令应答, desc = "摄像头立即拍摄命令应答")
    public void 摄像头立即拍摄命令应答(CameraShotReply packageData) {
        Header header = packageData.getHeader();
        String mobileNumber = header.getMobileNumber();
        Integer replyId = packageData.getSerialNumber();
        messageManager.put(mobileNumber + replyId, packageData);
    }

    @Mapping(types = 存储多媒体数据检索应答, desc = "存储多媒体数据检索应答")
    public void 存储多媒体数据检索应答(MediaDataQueryReply packageData, Session session) {
        Header header = packageData.getHeader();
        String mobileNumber = header.getMobileNumber();
        Integer replyId = packageData.getSerialNumber();
        messageManager.put(mobileNumber + replyId, packageData);
    }
    //=============================================================

    @Mapping(types = 终端心跳, desc = "终端心跳")
    public CommonResult heartBeat(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        //TODO
        CommonResult result = new CommonResult(resultHeader, 终端心跳, session.currentFlowId(), CommonResult.Success);
        return result;
    }

    @Mapping(types = 补传分包请求, desc = "补传分包请求")
    public CommonResult 补传分包请求(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 补传分包请求, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 终端注册, desc = "终端注册")
    public RegisterResult register(Register packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(终端注册应答, session.currentFlowId(), header.getMobileNumber());
        //TODO
        session.setTerminalId(header.getMobileNumber());
        sessionManager.put(Session.buildId(session.getChannel()), session);
        RegisterResult result = new RegisterResult(resultHeader, RegisterResult.Success, "111");
        return result;
    }

    @Mapping(types = 终端注销, desc = "终端注销")
    public CommonResult 终端注销(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        CommonResult result = new CommonResult(resultHeader, 终端注销, session.currentFlowId(), CommonResult.Success);
        return result;
    }

    @Mapping(types = 终端鉴权, desc = "终端鉴权")
    public CommonResult authentication(Authentication packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        //TODO
        session.setTerminalId(header.getMobileNumber());
        sessionManager.put(Session.buildId(session.getChannel()), session);
        CommonResult result = new CommonResult(resultHeader, 终端鉴权, session.currentFlowId(), CommonResult.Success);
        return result;
    }

    @Mapping(types = 终端升级结果通知, desc = "终端升级结果通知")
    public CommonResult 终端升级结果通知(TerminalUpgradeNotify packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        CommonResult result = new CommonResult(resultHeader, 终端升级结果通知, session.currentFlowId(), CommonResult.Success);
        return result;
    }

    @Mapping(types = 位置信息汇报, desc = "位置信息汇报")
    public CommonResult 位置信息汇报(PositionReport packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        CommonResult result = new CommonResult(resultHeader, 位置信息汇报, session.currentFlowId(), CommonResult.Success);
        return result;
    }

    @Mapping(types = 人工确认报警消息, desc = "人工确认报警消息")
    public CommonResult 人工确认报警消息(WarningMessage packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        CommonResult result = new CommonResult(resultHeader, 位置信息汇报, session.currentFlowId(), CommonResult.Success);
        return result;
    }

    @Mapping(types = 事件报告, desc = "事件报告")
    public CommonResult 事件报告(EventReport packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 事件报告, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 提问应答, desc = "提问应答")
    public CommonResult 提问应答(QuestionMessageReply packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 提问应答, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 信息点播_取消, desc = "信息点播/取消")
    public CommonResult 信息点播取消(MessageSubOperate packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 信息点播_取消, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 电话回拨, desc = "电话回拨")
    public CommonResult 电话回拨(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 电话回拨, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 设置电话本, desc = "设置电话本")
    public CommonResult 设置电话本(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 设置电话本, session.currentFlowId(), CommonResult.Success);
    }


    @Mapping(types = 设置圆形区域, desc = "设置圆形区域")
    public CommonResult 设置圆形区域(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 设置圆形区域, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 删除圆形区域, desc = "删除圆形区域")
    public CommonResult 删除圆形区域(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 删除圆形区域, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 设置矩形区域, desc = "设置矩形区域")
    public CommonResult 设置矩形区域(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 设置矩形区域, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 删除矩形区域, desc = "删除矩形区域")
    public CommonResult 删除矩形区域(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 删除矩形区域, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 设置多边形区域, desc = "设置多边形区域")
    public CommonResult 设置多边形区域(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 设置多边形区域, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 删除多边形区域, desc = "删除多边形区域")
    public CommonResult 删除多边形区域(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 删除多边形区域, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 设置路线, desc = "设置路线")
    public CommonResult 设置路线(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 设置路线, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 删除路线, desc = "删除路线")
    public CommonResult 删除路线(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 删除路线, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 行驶记录仪数据采集命令, desc = "行驶记录仪数据采集命令")
    public CommonResult 行驶记录仪数据采集命令(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 行驶记录仪数据采集命令, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 行驶记录仪数据上传, desc = "行驶记录仪数据上传")
    public CommonResult 行驶记录仪数据上传(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 行驶记录仪数据上传, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 行驶记录仪参数下传命令, desc = "行驶记录仪参数下达命令")
    public CommonResult 行驶记录仪参数下传命令(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 行驶记录仪参数下传命令, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 电子运单上报, desc = "电子运单上报")
    public CommonResult 电子运单上报(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 电子运单上报, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 驾驶员身份信息采集上报, desc = "驾驶员身份信息采集上报")
    public CommonResult 驾驶员身份信息采集上报(DriverIdentityInfo packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 驾驶员身份信息采集上报, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 定位数据批量上传, desc = "定位数据批量上传")
    public CommonResult 定位数据批量上传(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 定位数据批量上传, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = CAN总线数据上传, desc = "定位数据批量上传")
    public CommonResult CAN总线数据上传(CANBusReport packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, CAN总线数据上传, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 多媒体事件信息上传, desc = "多媒体事件信息上传")
    public CommonResult 多媒体事件信息上传(MediaEventReport packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 多媒体事件信息上传, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 多媒体数据上传, desc = "多媒体数据上传")
    public CommonResult 多媒体数据上传(MediaDataReport packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 多媒体数据上传, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 多媒体数据上传应答, desc = "多媒体数据上传应答")
    public CommonResult 多媒体数据上传应答(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 多媒体数据上传应答, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 摄像头立即拍摄命令, desc = "摄像头立即拍摄命令")
    public CommonResult 摄像头立即拍摄命令(PackageData<Header> packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 摄像头立即拍摄命令, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 数据上行透传, desc = "数据上行透传")
    public CommonResult passthrough(PassthroughPack packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 数据上行透传, session.currentFlowId(), CommonResult.Success);
    }

    @Mapping(types = 数据压缩上报, desc = "数据压缩上报")
    public CommonResult gzipPack(GZIPPack packageData, Session session) {
        Header header = packageData.getHeader();
        Header resultHeader = new Header(平台通用应答, session.currentFlowId(), header.getMobileNumber());
        return new CommonResult(resultHeader, 数据压缩上报, session.currentFlowId(), CommonResult.Success);
    }

}
package org.yzh.web.endpoint;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.session.MessageManager;
import org.yzh.framework.session.Session;
import org.yzh.framework.session.SessionManager;
import org.yzh.framework.session.SyncFuture;
import org.yzh.web.controller.ConsoleController;
import org.yzh.web.jt808.dto.Command1205;
import org.yzh.web.jt808.dto.T0001;
import org.yzh.web.jt808.dto.basics.Message;

import java.util.concurrent.TimeUnit;

import static org.yzh.web.jt808.common.JT1078.终端上传音视频资源列表;
import static org.yzh.web.jt808.common.JT808.平台通用应答;

@Endpoint
@Component
public class JT1078Endpoint {

    private static final Logger log = LoggerFactory.getLogger(JT1078Endpoint.class.getSimpleName());

    private SessionManager sessionManager = SessionManager.getInstance();

    private MessageManager messageManager = MessageManager.INSTANCE;


    @Autowired
    private ConsoleController consoleController;

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


    @Mapping(types = 终端上传音视频资源列表, desc = "终端上传音视频资源列表")
    public Message 终端上传音视频资源列表(Message<Command1205> message, Session session) {
        Command1205 body = message.getBody();
        if (body == null)
            return null;
        String mobileNo = message.getMobileNo();
        Integer replyId = body.getSerialNumber();


        T0001 result = new T0001(终端上传音视频资源列表, message.getSerialNo(), T0001.Success);
        return new Message(平台通用应答, session.currentFlowId(), message.getMobileNo(), result);
    }
}
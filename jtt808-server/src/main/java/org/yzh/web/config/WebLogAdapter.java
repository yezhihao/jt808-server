package org.yzh.web.config;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.protostar.SchemaManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageAdapter;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.commons.JT808;

import java.util.HashSet;

public class WebLogAdapter extends JTMessageAdapter {

    protected static final Logger log = LoggerFactory.getLogger(WebLogAdapter.class.getSimpleName());

    public static final HashSet<String> clientIds = new HashSet<>();
    public static final HashSet<Integer> ignoreMsgs = new HashSet<>();

    static {
        ignoreMsgs.add(JT808.定位数据批量上传);
    }

    private SimpMessagingTemplate messagingTemplate;

    public WebLogAdapter(SchemaManager schemaManager, SimpMessagingTemplate messagingTemplate) {
        super(schemaManager);
        this.messagingTemplate = messagingTemplate;
    }

    public WebLogAdapter(JTMessageEncoder messageEncoder, JTMessageDecoder messageDecoder, SimpMessagingTemplate messagingTemplate) {
        super(messageEncoder, messageDecoder);
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void encodeLog(Session session, JTMessage message, ByteBuf output) {
        boolean isClient = clientIds.contains(message.getClientId());
        if (isClient)
            messagingTemplate.convertAndSend("client/" + message.getClientId(), message + "\n" + ByteBufUtil.hexDump(output, 0, output.writerIndex()));
        if ((!ignoreMsgs.contains(message.getMessageId())) && (isClient || clientIds.isEmpty()))
            super.encodeLog(session, message, output);
    }

    @Override
    public void decodeLog(Session session, JTMessage message, ByteBuf input) {
        if (message != null) {
            boolean isClient = clientIds.contains(message.getClientId());
            if (isClient)
                messagingTemplate.convertAndSend("client/" + message.getClientId(), message + "\n" + ByteBufUtil.hexDump(input, 0, input.writerIndex()));
            if (!ignoreMsgs.contains(message.getMessageId()) && (isClient || clientIds.isEmpty()))
                super.decodeLog(session, message, input);

            if (!message.isVerified())
                log.error("<<<<<校验码错误session={},payload={}", session, ByteBufUtil.hexDump(input, 0, input.writerIndex()));
        }
    }

    public static void clearMessage() {
        synchronized (ignoreMsgs) {
            ignoreMsgs.clear();
        }
    }

    public static void addMessage(int messageId) {
        if (!ignoreMsgs.contains(messageId)) {
            synchronized (ignoreMsgs) {
                ignoreMsgs.add(messageId);
            }
        }
    }

    public static void removeMessage(int messageId) {
        if (ignoreMsgs.contains(messageId)) {
            synchronized (ignoreMsgs) {
                ignoreMsgs.remove(messageId);
            }
        }
    }

    public static void clearClient() {
        synchronized (clientIds) {
            clientIds.clear();
        }
    }

    public static void addClient(String clientId) {
        if (!clientIds.contains(clientId)) {
            synchronized (clientIds) {
                clientIds.add(clientId);
            }
        }
    }

    public static void removeClient(String clientId) {
        if (clientIds.contains(clientId)) {
            synchronized (clientIds) {
                clientIds.remove(clientId);
            }
        }
    }
}
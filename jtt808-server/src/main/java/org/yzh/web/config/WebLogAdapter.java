package org.yzh.web.config;

import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.protostar.SchemaManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageAdapter;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;

import java.util.HashSet;

public class WebLogAdapter extends JTMessageAdapter {

    private static final HashSet<String> clientIds = new HashSet<>();

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
        super.encodeLog(session, message, output);
        if (clientIds.contains(message.getClientId()))
            messagingTemplate.convertAndSend("/topic/subscribe/lbs/" + message.getClientId(), message + "\n" + ByteBufUtil.hexDump(output, 0, output.writerIndex()));
    }

    @Override
    public void decodeLog(Session session, JTMessage message, ByteBuf input) {
        super.decodeLog(session, message, input);
        if (message != null) {
            if (!message.isVerified())
                log.error("<<<<<校验码错误session={},payload={}", session, ByteBufUtil.hexDump(input, 0, input.writerIndex()));
            if (clientIds.contains(message.getClientId()))
                messagingTemplate.convertAndSend("/topic/subscribe/lbs/" + message.getClientId(), message + "\n" + ByteBufUtil.hexDump(input, 0, input.writerIndex()));
        }
    }

    public static void clear() {
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
package org.yzh.web.endpoint;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.yzh.protocol.basics.JTMessage;

import java.util.HashSet;

@Component
public class LoggingPusher {

    private HashSet<String> clientIds = new HashSet<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void send(JTMessage message, ByteBuf buf) {
        if (clientIds.contains(message.getClientId()))
            messagingTemplate.convertAndSend("/topic/subscribe/lbs/" + message.getClientId(), message + "\n" + ByteBufUtil.hexDump(buf, 0, buf.writerIndex()));
    }

    public void clear() {
        synchronized (clientIds) {
            clientIds.clear();
        }
    }

    public void addClient(String clientId) {
        if (!clientIds.contains(clientId)) {
            synchronized (clientIds) {
                clientIds.add(clientId);
            }
        }
    }

    public void removeClient(String clientId) {
        if (clientIds.contains(clientId)) {
            synchronized (clientIds) {
                clientIds.remove(clientId);
            }
        }
    }
}
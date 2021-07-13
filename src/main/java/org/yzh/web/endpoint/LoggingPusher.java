package org.yzh.web.endpoint;

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

    public void info(JTMessage message, String raw) {
        if (clientIds.contains(message.getClientId()))
            messagingTemplate.convertAndSend("/topic/subscribe/lbs/" + message.getClientId(), message + "\n" + raw);
    }

    public void clear() {
        synchronized (clientIds) {
            clientIds.clear();
        }
    }

    public void addClient(String clientId) {
        synchronized (clientIds) {
            clientIds.add(clientId);
        }
    }

    public void removeClient(String clientId) {
        synchronized (clientIds) {
            clientIds.remove(clientId);
        }
    }
}
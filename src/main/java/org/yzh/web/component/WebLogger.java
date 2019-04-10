package org.yzh.web.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.framework.log.Logger;
import org.yzh.framework.message.AbstractMessage;

@Component
public class WebLogger extends Logger {

    @Autowired
    WebSocketMessageHandler webSocketMessageHandler;

    public String logMessage(String type, AbstractMessage message, String hex) {
        String log = super.logMessage(type, message, hex);
        webSocketMessageHandler.broadcast(log);
        return log;
    }
}
package org.yzh.web.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.framework.log.Logger;
import org.yzh.framework.message.PackageData;

@Component
public class WebLogger extends Logger {

    @Autowired
    WebSocketMessageHandler webSocketMessageHandler;

    public String logMessage(String type, PackageData packageData, String message) {
        String log = super.logMessage(type, packageData, message);
        webSocketMessageHandler.broadcast(log);
        return log;
    }
}
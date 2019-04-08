package org.yzh.framework.log;

import org.slf4j.LoggerFactory;
import org.yzh.framework.TCPServerHandler;
import org.yzh.framework.message.PackageData;
import org.yzh.framework.session.Session;

public class Logger {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TCPServerHandler.class.getSimpleName());

    public String logMessage(String type, PackageData packageData, String message) {
        String log = type + " " + message;
        logger.info(log);
        return log;
    }

    public String logEvent(String event, Session session) {
        String log = event + " " + session.getTerminalId();
        logger.info(log);
        return log;
    }
}
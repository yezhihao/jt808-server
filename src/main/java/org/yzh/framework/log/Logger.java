package org.yzh.framework.log;

import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.session.Session;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class Logger {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class.getSimpleName());

    public String logMessage(String type, AbstractHeader message, String hex) {
        String log = type + " " + hex;
        logger.info(log);
        return log;
    }

    public String logEvent(String event, Session session) {
        String log = event + " " + session.getTerminalId();
        logger.info(log);
        return log;
    }
}
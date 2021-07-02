package org.yzh.protocol.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.web.commons.StrUtils;

import java.util.Arrays;
import java.util.Objects;

public class LoggingFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class.getSimpleName());

    private static volatile String CLIENT_ID = null;

    private static volatile int[] IGNORE_MSG_IDS = null;

    public static boolean info(JTMessage message) {
        return log.isInfoEnabled() &&
                (CLIENT_ID == null || CLIENT_ID.equals(message.getClientId())) &&
                (IGNORE_MSG_IDS == null || Arrays.binarySearch(IGNORE_MSG_IDS, message.getMessageId()) < 0);
    }

    public static void setFilter(String clientId, String ignoreMsgIds) {
        if (StrUtils.isNotBlank(clientId) && !Objects.equals(clientId, CLIENT_ID))
            CLIENT_ID = clientId;

        if (StrUtils.isNotBlank(ignoreMsgIds))
            IGNORE_MSG_IDS = Arrays.stream(ignoreMsgIds.split(" ")).mapToInt(s -> Integer.parseInt(s, 16)).distinct().sorted().toArray();
    }

    public static void clear() {
        CLIENT_ID = null;
        IGNORE_MSG_IDS = null;
    }

    public static String getClientId() {
        return CLIENT_ID;
    }

    public static String[] getIgnoreMsgIds() {
        return Arrays.stream(IGNORE_MSG_IDS).mapToObj(i -> StrUtils.leftPad(Integer.toHexString(i), 4, '0')).toArray(String[]::new);
    }
}
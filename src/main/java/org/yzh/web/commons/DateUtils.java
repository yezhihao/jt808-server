package org.yzh.web.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DateUtils {

    private static final Logger log = LoggerFactory.getLogger(DateUtils.class.getSimpleName());

    public static final DateTimeFormatter yyMMddHHmmss = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final ZoneOffset GMT8 = ZoneOffset.ofHours(8);

    public static LocalDateTime parse(String str) {
        try {
            return LocalDateTime.parse(str, yyMMddHHmmss);
        } catch (Exception e) {
            log.error("日期格式错误：[{}]", str);
            return null;
        }
    }
}
package org.yzh.web.commons;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DateUtils {

    private static final Logger log = LoggerFactory.getLogger(DateUtils.class.getSimpleName());

    public static final FastDateFormat yyyyMMddHHmmss = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    public static final FastDateFormat yyMMddHHmmss = FastDateFormat.getInstance("yyMMddHHmmss");

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final ZoneOffset GMT8 = ZoneOffset.ofHours(8);

    public static Date parse(String str) {
        try {
            return yyMMddHHmmss.parse(str);
        } catch (ParseException e) {
            log.error("日期格式错误：[{}]", str);
            return null;
        }
    }
}
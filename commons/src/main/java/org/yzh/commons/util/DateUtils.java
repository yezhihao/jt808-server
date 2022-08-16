package org.yzh.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class DateUtils {

    private static final Logger log = LoggerFactory.getLogger(DateUtils.class.getSimpleName());

    public static final DateTimeFormatter yyMMddHHmmss = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    public static final DateTimeFormatter yyyyMMdd = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR_OF_ERA, 4)
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .toFormatter();

    public static final DateTimeFormatter yyMMdd = new DateTimeFormatterBuilder()
            .appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 2, LocalDate.now().minusYears(80))
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .toFormatter();

    public static final DateTimeFormatter yy = new DateTimeFormatterBuilder()
            .appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 2, LocalDate.now().minusYears(80))
            .toFormatter();

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final ZoneOffset GMT8 = ZoneOffset.ofHours(8);

    public static long getMillis(LocalDateTime dateTime) {
        return dateTime.atZone(GMT8).toInstant().toEpochMilli();
    }

    public static LocalDateTime getDateTime(Long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), GMT8);
    }

    public static LocalDateTime parse(String str) {
        try {
            return LocalDateTime.parse(str, yyMMddHHmmss);
        } catch (Exception e) {
            log.error("日期格式错误：[{}]", str);
            return null;
        }
    }
}
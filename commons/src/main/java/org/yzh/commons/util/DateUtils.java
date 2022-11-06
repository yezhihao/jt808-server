package org.yzh.commons.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class DateUtils {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

    public static final ZoneOffset ZONE = ZoneOffset.systemDefault().getRules().getStandardOffset(Instant.now());

    private static final TemporalQuery<LocalDate> dateQuery = TemporalQueries.localDate();

    private static final TemporalQuery<LocalTime> timeQuery = TemporalQueries.localTime();

    public static long currentTimeSecond() {
        return System.currentTimeMillis() / 1000L;
    }

    public static long getMillis(LocalDateTime dateTime) {
        return dateTime.toInstant(ZONE).toEpochMilli();
    }

    public static LocalDateTime getDateTime(Long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        return LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), ZONE);
    }

    public static LocalDateTime getDateTime(Instant instant) {
        return LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), ZONE);
    }

    public static LocalDateTime parse(String str) {
        return parse(str, yyMMddHHmmss);
    }

    public static LocalDateTime parse(String str, DateTimeFormatter df) {
        try {
            TemporalAccessor temporal = df.parse(str);
            LocalDate date = temporal.query(dateQuery);
            LocalTime time = temporal.query(timeQuery);
            return LocalDateTime.of(date, time);
        } catch (Exception e) {
            return null;
        }
    }
}
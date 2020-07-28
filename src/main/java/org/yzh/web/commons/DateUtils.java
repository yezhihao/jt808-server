package org.yzh.web.commons;

import org.apache.commons.lang3.time.FastDateFormat;

import java.time.format.DateTimeFormatter;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class DateUtils {

    public static final FastDateFormat FastDateFormatter = FastDateFormat.getInstance("yyMMddHHmmss");

    public static final DateTimeFormatter yyMMddHHmmss = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

}
package org.yzh.framework.commons.transform;

import io.netty.buffer.ByteBuf;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * BCD编码工具类
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Bcd {

    public static final int YEAR = LocalDate.now().getYear();
    public static final int YEAR_RANGE = YEAR - 30;
    public static final int HUNDRED_YEAR = YEAR_RANGE / 100 * 100;

    /** BCD转String */
    public static String toString(byte[] bcd) {
        return new String(toChars(bcd));
    }

    /** BCD转char[] */
    public static char[] toChars(byte[] bcd) {
        char[] chars = new char[bcd.length * 2];
        for (int i = 0, j = 0; i < bcd.length; i++) {
            chars[j++] = (char) (48 + (bcd[i] >> 4 & 0xf));
            chars[j++] = (char) (48 + (bcd[i] & 0xf));
        }
        return chars;
    }

    /** String转BCD */
    public static byte[] from(String str) {
        return from(str.toCharArray());
    }

    /** char[]转BCD */
    public static byte[] from(char[] chars) {
        byte[] bcd = new byte[chars.length / 2];
        for (int i = 0, j = 0; i < bcd.length; i++) {
            bcd[i] = (byte) ((chars[j++] - 48 << 4) | ((chars[j++] - 48 & 0xf)));
        }
        return bcd;
    }

    /** 时间转BCD (yyMMddHHmmss) */
    public static byte[] from(LocalDateTime dateTime) {
        byte[] bcd = new byte[6];
        bcd[0] = bcd(dateTime.getYear() % 100);
        bcd[1] = bcd(dateTime.getMonthValue());
        bcd[2] = bcd(dateTime.getDayOfMonth());
        bcd[3] = bcd(dateTime.getHour());
        bcd[4] = bcd(dateTime.getMinute());
        bcd[5] = bcd(dateTime.getSecond());
        return bcd;
    }

    /** BCD转时间 (yyMMddHHmmss) */
    public static LocalDateTime toDateTime(byte[] bcd) {
        int i = bcd.length - 1;
        int year = HUNDRED_YEAR + num(bcd[i - 5]);
        if (year < YEAR_RANGE)
            year += 100;
        return LocalDateTime.of(
                year,
                num(bcd[i - 4]),
                num(bcd[i - 3]),
                num(bcd[i - 2]),
                num(bcd[i - 1]),
                num(bcd[i]));
    }

    /** 日期转BCD (yyMMdd) */
    public static byte[] from(LocalDate date) {
        byte[] bcd = new byte[3];
        bcd[0] = bcd(date.getYear() % 100);
        bcd[1] = bcd(date.getMonthValue());
        bcd[2] = bcd(date.getDayOfMonth());
        return bcd;
    }

    /** BCD转日期 (yyMMdd) */
    public static LocalDate toDate(byte[] bcd) {
        int i = bcd.length - 1;
        int year = HUNDRED_YEAR + num(bcd[i - 2]);
        if (year < YEAR_RANGE)
            year += 100;
        return LocalDate.of(year, num(bcd[i - 1]), num(bcd[i]));
    }

    /** BCD转时间 (HHMM) */
    public static LocalTime readTime2(ByteBuf input) {
        return LocalTime.of(num(input.readByte()), num(input.readByte()));
    }

    /** BCD转时间 (HHMM) */
    public static void writeTime2(ByteBuf output, LocalTime time) {
        output.writeByte(bcd(time.getHour()));
        output.writeByte(bcd(time.getMinute()));
    }

    public static byte bcd(int num) {
        return (byte) ((num / 10 << 4) | (num % 10 & 0xf));
    }

    public static int num(byte bcd) {
        return (bcd >> 4 & 0xf) * 10 + (bcd & 0xf);
    }

    public static int indexOf(char[] chars, char pad) {
        int i = 0, len = chars.length;
        while (i < len && chars[i] == pad) i++;
        return i;
    }
}
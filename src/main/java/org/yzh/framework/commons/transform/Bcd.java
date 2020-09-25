package org.yzh.framework.commons.transform;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public static String toStr(byte[] bcd) {
        StringBuilder str = new StringBuilder(bcd.length * 2);
        for (int i = 0; i < bcd.length; i++) {
            str.append(bcd[i] >> 4 & 0xf).append(bcd[i] & 0xf);
        }
        return str.toString();
    }

    /** String转BCD */
    public static byte[] fromStr(String str) {
        byte[] bytes = str.getBytes();
        byte[] bcd = new byte[bytes.length / 2];
        for (int i = 0; i < bcd.length; i++) {
            bcd[i] = (byte) (bytes[2 * i] << 4 | (bytes[2 * i + 1] & 0xf));
        }
        return bcd;
    }

    /** 时间转BCD (yyMMddHHmmss) */
    public static byte[] fromDateTime(LocalDateTime dateTime) {
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

    public static byte bcd(int num) {
        return (byte) (num / 10 << 4 | (num % 10 & 0xf));
    }

    public static int num(byte bcd) {
        return (bcd >> 4 & 0xf) * 10 + (bcd & 0xf);
    }

    /** 检查字节数组长度，填充或截断 */
    public static byte[] checkRepair(byte[] src, int length) {
        int srcPos = src.length - length;

        if (srcPos > 0) {
            byte[] dest = new byte[length];
            System.arraycopy(src, srcPos, dest, 0, length);
            return dest;
        } else if (srcPos < 0) {
            byte[] dest = new byte[length];
            System.arraycopy(src, 0, dest, -srcPos, src.length);
            return dest;
        }
        return src;
    }

    public static String leftPad(String str, int size, char pad) {
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0)
            return str;

        char[] padding = new char[pads];
        for (int i = 0; i < pads; i++)
            padding[i] = pad;
        return new String(padding).concat(str);
    }

    public static String leftTrim(String str, char pad) {
        char[] val = str.toCharArray();
        int len = val.length;

        int st = 0;
        while (st < len && val[st] == pad)
            st++;
        return (st > 0 && st < len) ? str.substring(st) : str;
    }
}
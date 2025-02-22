package org.yzh.commons.util;

import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class StrUtils {

    public static <T> T ifNull(T obj, T defObj) {
        return (obj != null) ? obj : defObj;
    }

    public static <T> T ifNull(T obj, Supplier<T> defObj) {
        return (obj != null) ? obj : defObj.get();
    }

    public static String ifBlank(String str, String defStr) {
        return (str == null || str.isBlank()) ? defStr : str;
    }

    public static Long parseLong(String num) {
        return parseLong(num, null);
    }

    public static Long parseLong(String num, Long defVal) {
        try {
            return Long.parseLong(num);
        } catch (NumberFormatException e) {
            return defVal;
        }
    }

    public static String toUnderline(String str) {
        StringBuilder result = new StringBuilder(str.length() + 4);
        char[] chars = str.toCharArray();

        result.append(Character.toLowerCase(chars[0]));

        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c))
                result.append('_').append(Character.toLowerCase(c));
            else
                result.append(c);
        }
        return result.toString();
    }

    public static String leftPad(String str, int size, char ch) {
        int length = str.length();
        int pads = size - length;
        if (pads > 0) {
            char[] result = new char[size];
            str.getChars(0, length, result, pads);
            while (pads > 0)
                result[--pads] = ch;
            return new String(result);
        }
        return str;
    }

    public static String rightPad(String str, int size, char ch) {
        int length = str.length();
        if (length < size) {
            char[] result = new char[size];
            str.getChars(0, length, result, 0);
            while (length < size)
                result[length++] = ch;
            return new String(result);
        }
        return str;
    }

    public static Set<Integer> toSet(int... num) {
        if (num == null || num.length == 0) {
            return Set.of();
        }
        Set<Integer> result;
        if (num.length <= 3) {
            result = new TreeSet<>();
        } else {
            result = new HashSet<>(num.length << 1);
        }
        for (int i : num) {
            result.add(i);
        }
        return result;
    }

    public static boolean isNum(CharSequence val) {
        if (!StringUtils.hasText(val)) {
            return false;
        }
        int sz = val.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(val.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String truncateDecimal(double num, int maximumFractionDigits) {
        return truncateDecimal(Double.toString(num), maximumFractionDigits);
    }

    public static String truncateDecimal(String num, int maximumFractionDigits) {
        return truncateDecimal(num, maximumFractionDigits, new StringBuilder(10)).toString();
    }

    public static StringBuilder truncateDecimal(double num, int maximumFractionDigits, StringBuilder sb) {
        return truncateDecimal(Double.toString(num), maximumFractionDigits, sb);
    }

    public static StringBuilder truncateDecimal(String num, int maximumFractionDigits, StringBuilder sb) {
        int end = num.indexOf('.') + 1 + maximumFractionDigits;
        if (end < num.length()) {
            sb.append(num, 0, end);
        } else {
            sb.append(num);
        }
        return sb;
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String bytes2Hex(byte[] bytes) {
        char[] hex = new char[bytes.length << 1];
        for (int j = 0, i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            hex[j++] = hexCode[(b >> 4) & 0xF];
            hex[j++] = hexCode[(b & 0xF)];
        }
        return new String(hex);
    }

    public static byte[] hex2Bytes(String hex) {
        final int len = hex.length();

        if (len % 2 != 0) {
            throw new IllegalArgumentException("hexBinary needs to be even-length: " + hex);
        }

        byte[] out = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {

            int h = hexToBin(hex.charAt(i));
            int l = hexToBin(hex.charAt(i + 1));
            if (h == -1 || l == -1) {
                throw new IllegalArgumentException("contains illegal character for hexBinary: " + hex);
            }
            out[i >> 1] = (byte) (h * 16 + l);
        }
        return out;
    }

    public static int hexToBin(char ch) {
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - ('A' - 10);
        }
        if ('a' <= ch && ch <= 'f') {
            return ch - ('a' - 10);
        }
        return -1;
    }

    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
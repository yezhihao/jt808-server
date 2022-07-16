package org.yzh.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class StrUtils {

    public static final int[] EMPTY = new int[0];

    public static final Integer[] EMPTY_ = new Integer[0];

    public static int[] toInts(String str, String delimiter) {
        String[] split = str.split(delimiter);
        int[] result = new int[split.length];
        for (int i = 0; i < split.length; i++)
            result[i] = Integer.parseInt(split[i]);
        return result;
    }

    public static double[] toDoubles(String str, String delimiter) {
        String[] split = str.split(delimiter);
        double[] result = new double[split.length];
        for (int i = 0; i < split.length; i++)
            result[i] = Double.parseDouble(split[i]);
        return result;
    }

    public static byte[] toBytes(String str, String delimiter) {
        String[] split = str.split(delimiter);
        byte[] result = new byte[split.length];
        for (int i = 0; i < split.length; i++)
            result[i] = (byte) Integer.parseInt(split[i]);
        return result;
    }

    public static String merge(String delimiter, Object... value) {
        if (value == null || value.length == 0)
            return null;

        StringBuilder result = new StringBuilder(value.length * 5);
        for (Object id : value)
            result.append(id).append(delimiter);

        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static String merge(String delimiter, int... value) {
        if (value == null || value.length == 0)
            return null;

        StringBuilder result = new StringBuilder(value.length * 5);
        for (Object id : value)
            result.append(id).append(delimiter);

        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static int[] toInts(Integer[] src) {
        if (src == null || src.length == 0)
            return EMPTY;

        int[] dest = new int[src.length];
        for (int i = 0; i < src.length; i++)
            dest[i] = src[i];
        return dest;
    }

    public static Integer[] toInts(int[] src) {
        if (src == null || src.length == 0)
            return EMPTY_;

        Integer[] dest = new Integer[src.length];
        for (int i = 0; i < src.length; i++)
            dest[i] = src[i];
        return dest;
    }

    public static Integer parseInt(String num) {
        if (isBlank(num))
            return null;
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return null;
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

    public static String subPrefix(String str, String prefix) {
        if (str != null && str.startsWith(prefix))
            str = str.substring(prefix.length());
        return str;
    }

    public static Map newMap(Object... entrys) {
        Map result = new HashMap((int) (entrys.length / 1.5) + 1);
        for (int i = 0; i < entrys.length; )
            result.put(entrys[i++], entrys[i++]);
        return result;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        return str == null || str.length() == 0 || str.trim().length() == 0;
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

    public static int[] toArray(Collection<Integer> list) {
        if (list == null || list.isEmpty())
            return null;

        int[] result = new int[list.size()];
        int i = 0;
        for (Integer e : list) {
            if (e != null)
                result[i++] = e;
        }
        return result;
    }

    public static Set<Integer> toSet(int... num) {
        if (num == null || num.length == 0) {
            return Collections.EMPTY_SET;
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

    public static boolean isNum(String val) {
        if (isBlank(val)) {
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

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter(7680);
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
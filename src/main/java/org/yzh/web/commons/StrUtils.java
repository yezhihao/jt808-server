package org.yzh.web.commons;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
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

    public static byte[] toBytes(String str, String delimiter) {
        String[] split = str.split(delimiter);
        byte[] result = new byte[split.length];
        for (int i = 0; i < split.length; i++)
            result[i] = (byte) Integer.parseInt(split[i]);
        return result;
    }

    public static String marge(String delimiter, Object... value) {
        if (value == null || value.length == 0)
            return null;

        StringBuilder result = new StringBuilder(value.length * 5);
        for (Object id : value)
            result.append(id).append(delimiter);

        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public static String marge(String delimiter, int... value) {
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

    public static String toUnderline(String str) {
        StringBuilder result = new StringBuilder(str.length() + 4);
        char[] chars = str.toCharArray();

        result.append(Character.toLowerCase(chars[0]));

        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c))
                result.append("_").append(Character.toLowerCase(c));
            else
                result.append(c);
        }
        return result.toString();
    }
}
package org.yzh.framework.commons.lang;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    public static final String NUMERIC = "0123456789";
    public static final String ALPHABETIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ALPHANUMERIC = "_0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字、下划线,首字母不能为数字)
     */
    public static String nextString(int length) {
        return nextString(ALPHANUMERIC, length);
    }


    public static String nextString(String source, int length) {
        Random random = ThreadLocalRandom.current();

        StringBuilder sb = new StringBuilder(length);
        sb.append(source.charAt(random.nextInt(source.length())));
        for (int i = 1; i < length; i++)
            sb.append(source.charAt(random.nextInt(source.length())));
        return sb.toString();
    }


    /**
     * 返回一个定长的随机数字
     */
    public static String nextNumber(int length) {
        Random random = ThreadLocalRandom.current();

        StringBuilder result = new StringBuilder(19);
        do
            result.append(Math.abs(random.nextLong()));
        while (result.length() < length);

        return result.substring(0, length);
    }

}
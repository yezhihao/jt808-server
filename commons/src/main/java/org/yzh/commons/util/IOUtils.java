package org.yzh.commons.util;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class IOUtils {

    public static void close(AutoCloseable a) {
        if (a != null)
            try {
                a.close();
            } catch (Exception ignored) {
            }
    }

    public static void close(AutoCloseable a1, AutoCloseable a2) {
        close(a1);
        close(a2);
    }

    public static void close(AutoCloseable a1, AutoCloseable a2, AutoCloseable a3) {
        close(a1);
        close(a2);
        close(a3);
    }
}
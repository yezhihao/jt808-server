package org.yzh.framework.commons.transform;

/**
 * 32位整型的二进制读写
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class Bin {

    private static final int[] bits = new int[33];

    static {
        bits[1] = 1;
        for (int i = 2; i < bits.length; i++) {
            bits[i] = bits[i - 1] * 2;
        }
    }

    /** 读取n的第i位 */
    public static boolean get(int n, int i) {
        return (n & bits[i]) == bits[i];
    }

    /** 写入bool到n的第i位 */
    public static int set(int n, int i, boolean bool) {
        return bool ? n | bits[i] : n ^ (n & bits[i]);
    }
}
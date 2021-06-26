package org.yzh.protocol.commons;

/**
 * 32位整型的二进制读写
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Bit {

    private static final int[] bits = new int[32];

    static {
        bits[0] = 1;
        for (int i = 1; i < bits.length; i++) {
            bits[i] = bits[i - 1] << 1;
        }
    }

    /**
     * 读取n的第i位
     * @param n int32
     * @param i 取值范围0~31
     */
    public static boolean get(int n, int i) {
        return (n & bits[i]) == bits[i];
    }

    /**
     * 写入bool到n的第i位
     * @param n int32
     * @param i 取值范围0~31
     */
    public static int set(int n, int i, boolean bool) {
        return bool ? n | bits[i] : n ^ (n & bits[i]);
    }

    /**
     * 按位写入int
     * 数组第一个int为首位
     */
    public static int writeInt(int... bit) {
        int r = 0;
        for (int i = 0; i < bit.length; i++)
            r = bit[i] > 0 ? (r | bits[i]) : (r ^ (r & bits[i]));
        return r;
    }
}
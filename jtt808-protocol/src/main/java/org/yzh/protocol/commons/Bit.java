package org.yzh.protocol.commons;

/**
 * 32位整型的二进制读写
 * << 左移
 * &与 同为1返回1
 * |或 其中一个为1返回1
 * ^异或 相同为0不同为1
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Bit {

    /**
     * 判断n的第i位
     * @param n int32
     * @param i 取值范围0~31
     */
    public static boolean isTrue(int n, int i) {
        return get(n, i) > 0;
    }

    /**
     * 读取n的第i位
     * @param n int32
     * @param i 取值范围0~31
     */
    public static int get(int n, int i) {
        return (1 << i) & n;
    }

    /**
     * 设置n的第i位为1
     * @param n int32
     * @param i 取值范围0~31
     */
    public static int set1(int n, int i) {
        return (1 << i) | n;
    }

    /**
     * 设置n的第i位为0
     * @param n int32
     * @param i 取值范围0~31
     */
    public static int set0(int n, int i) {
        return get(n, i) ^ n;
    }

    /**
     * 写入bool到n的第i位
     * @param n int32
     * @param i 取值范围0~31
     */
    public static int set(int n, int i, boolean bool) {
        return bool ? set1(n, i) : set0(n, i);
    }

    /**
     * 按位写入int
     * 数组第一个int为首位
     */
    public static int writeInt(int... bit) {
        int n = 0;
        for (int i = 0; i < bit.length; i++)
            n = set(n, i, bit[i] > 0);
        return n;
    }
}
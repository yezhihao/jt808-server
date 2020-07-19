package org.yzh.framework.commons.transform;

/**
 * 字节数组与int之间的转换
 *
 * @author zhihao.ye (yezhihaoo@gmail.com)
 */
public class Bit {

    public static byte[] toBytes(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) (num >>> 24);
        result[1] = (byte) (num >>> 16);
        result[2] = (byte) (num >>> 8);
        result[3] = (byte) (num);
        return result;
    }

    public static int toUInt16(byte[] bytes, int start) {
        int value = 0;
        int m = start + 2;
        for (int i = start; i < m; i++) {
            int shift = (m - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return value;
    }

    public static int toUInt32(byte[] bytes, int start) {
        int value = 0;
        int m = start + 4;
        for (int i = start; i < m; i++) {
            int shift = (m - 1 - i) * 8;
            value += (bytes[i] & 0x000000FF) << shift;
        }
        return value;
    }

    public static int toUInt32(byte b) {
        int value = 0;
        int m = 4;
        int shift = (m - 1 - 3) * 8;
        value += (b & 0x000000FF) << shift;
        return value;
    }
}
package org.yzh.framework.commons.transform;

/**
 * 字节数组与int之间的转换
 *
 * @author zhihao.ye (1527621790@qq.com)
 */
public class Bit {

    public static byte[] write4Byte(byte[] bytes, int start, int num) {
        bytes[start] = (byte) (num >>> 24);
        bytes[++start] = (byte) (num >>> 16);
        bytes[++start] = (byte) (num >>> 8);
        bytes[++start] = (byte) (num);
        return bytes;
    }

    public static byte[] write2Byte(byte[] bytes, int start, int num) {
        bytes[start] = (byte) (num >>> 8);
        bytes[++start] = (byte) (num);
        return bytes;
    }

    public static int readInt16(byte[] bytes, int start) {
        return ((bytes[start] & 0xFF) << 8) | (bytes[++start] & 0xFF);
    }

    public static int readInt32(byte[] bytes, int start) {
        return ((bytes[start] & 0xFF) << 24) |
                ((bytes[++start] & 0xFF) << 16) |
                ((bytes[++start] & 0xFF) << 8) |
                ((bytes[++start] & 0xFF));
    }

    public static int readInt32(byte b) {
        int value = 0;
        int m = 4;
        int shift = (m - 1 - 3) * 8;
        value += (b & 0x000000FF) << shift;
        return value;
    }
}
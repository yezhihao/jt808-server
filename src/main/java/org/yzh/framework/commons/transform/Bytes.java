package org.yzh.framework.commons.transform;

/**
 * 字节数组与int之间的转换
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class Bytes {

    public static int getInt16(byte[] memory, int index) {
        return ((memory[index] & 0xff) << 8) | (memory[index + 1] & 0xff);
    }

    public static int getInt32(byte[] bytes, int start) {
        return  ((bytes[start]     & 0xff) << 24) |
                ((bytes[start + 1] & 0xff) << 16) |
                ((bytes[start + 2] & 0xff) <<  8) |
                ((bytes[start + 3] & 0xff));
    }

    public static byte[] setInt16(byte[] memory, int index, int value) {
        memory[index] = (byte) (value >>> 8);
        memory[index + 1] = (byte) (value);
        return memory;
    }

    public static byte[] setInt32(byte[] memory, int index, int value) {
        memory[index]     = (byte) (value >>> 24);
        memory[index + 1] = (byte) (value >>> 16);
        memory[index + 2] = (byte) (value >>>  8);
        memory[index + 3] = (byte) (value);
        return memory;
    }
}
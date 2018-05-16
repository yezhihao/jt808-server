package org.yzh.framework.commons.transform;

import io.netty.buffer.ByteBuf;

public class BitOperator {

    /**
     * 把一个整型改为byte
     */
    public static byte integerTo1Byte(int value) {
        return (byte) (value & 0xFF);
    }

    /**
     * 把一个整型改为1位的byte数组
     */
    public static byte[] integerTo1Bytes(int value) {
        byte[] result = new byte[1];
        result[0] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把一个整型改为2位的byte数组
     */
    public static byte[] integerTo2Bytes(int value) {
        byte[] result = new byte[2];
        result[0] = (byte) ((value >>> 8) & 0xFF);
        result[1] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把一个整型改为3位的byte数组
     */
    public static byte[] integerTo3Bytes(int value) {
        byte[] result = new byte[3];
        result[0] = (byte) ((value >>> 16) & 0xFF);
        result[1] = (byte) ((value >>> 8) & 0xFF);
        result[2] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把一个整型改为4位的byte数组
     */
    public static byte[] integerTo4Bytes(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) ((value >>> 24) & 0xFF);
        result[1] = (byte) ((value >>> 16) & 0xFF);
        result[2] = (byte) ((value >>> 8) & 0xFF);
        result[3] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把byte[]转化位整型,通常为指令用
     */
    public static int byteToInteger(byte[] value) {
        if (value.length == 1) {
            return oneByteToInteger(value[0]);
        } else if (value.length == 2) {
            return twoBytesToInteger(value);
        } else if (value.length == 3) {
            return threeBytesToInteger(value);
        } else {
            return fourBytesToInteger(value);
        }
    }

    /**
     * 把一个byte转化位整型
     */
    public static int oneByteToInteger(byte value) {
        return (int) value & 0xFF;
    }

    /**
     * 把一个2位的数组转化位整型
     */
    public static int twoBytesToInteger(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        return ((temp0 << 8) + temp1);
    }

    /**
     * 把一个3位的数组转化位整型
     */
    public static int threeBytesToInteger(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        return ((temp0 << 16) + (temp1 << 8) + temp2);
    }

    /**
     * 把一个4位的数组转化位整型,通常为指令用
     */
    public static int fourBytesToInteger(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return ((temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    /**
     * 把一个数组转化长整型
     */
    public static long bytes2Long(byte[] value) {
        long result = 0;
        int len = value.length;
        int temp;
        for (int i = 0; i < len; i++) {
            temp = (len - 1 - i) * 8;
            if (temp == 0) {
                result += (value[i] & 0x0ff);
            } else {
                result += (value[i] & 0x0ff) << temp;
            }
        }
        return result;
    }

    /**
     * 把一个长整型改为byte数组
     */
    public static byte[] longToBytes(long value) {
        return longToBytes(value, 8);
    }

    /**
     * 把一个长整型改为byte数组
     */
    public static byte[] longToBytes(long value, int len) {
        byte[] result = new byte[len];
        int temp;
        for (int i = 0; i < len; i++) {
            temp = (len - 1 - i) * 8;
            if (temp == 0) {
                result[i] += (value & 0x0ff);
            } else {
                result[i] += (value >>> temp) & 0x0ff;
            }
        }
        return result;
    }

    /**
     * 左侧补齐
     */
    public static byte[] leftPad(byte[] bytes, int length, byte padByte) {
        int i = length - bytes.length;
        if (i <= 0)
            return bytes;
        byte[] result = new byte[length];
        System.arraycopy(bytes, 0, result, i, bytes.length);
        for (int j = 0; j < i; j++)
            result[j] = padByte;
        return result;
    }

    public static byte xor(ByteBuf byteBuf) {
        byte cs = 0;
        while (byteBuf.isReadable())
            cs ^= byteBuf.readByte();
        byteBuf.resetReaderIndex();
        return cs;
    }
}
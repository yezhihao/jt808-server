package org.yzh.framework.commons.transform;

public class Bcd {

    /**
     * 8421BCD转String
     */
    public static String bcdToStr(byte[] bcd) {
        StringBuilder str = new StringBuilder(bcd.length * 2);
        for (int i = 0; i < bcd.length; i++) {
            str.append(bcd[i] >> 4 & 0xf).append(bcd[i] & 0xf);
        }
        return str.toString();
    }

    /**
     * String转8421BCD
     */
    public static byte[] strToBcd(String str) {
        byte[] bytes = str.getBytes();
        byte[] bcd = new byte[bytes.length / 2];
        for (int i = 0; i < bcd.length; i++) {
            bcd[i] = (byte) (bytes[2 * i] << 4 | (bytes[2 * i + 1] & 0xf));
        }
        return bcd;
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
}
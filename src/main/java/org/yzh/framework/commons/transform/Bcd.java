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
     * 检查字节数组长度，填充或截断
     */
    public static byte[] checkRepair(byte[] src, int length) {
        int srcPos = src.length - length;

        if (srcPos > 0) {
            byte[] dest = new byte[length];
            System.arraycopy(src, srcPos, dest, 0, length);
            return dest;
        } else if (srcPos < 0) {
            byte[] dest = new byte[length];
            System.arraycopy(src, 0, dest, -srcPos, src.length);
            return dest;
        }
        return src;
    }

    public static String leftPad(String str, int size, char pad) {
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0)
            return str;

        char[] padding = new char[pads];
        for (int i = 0; i < pads; i++)
            padding[i] = pad;
        return new String(padding).concat(str);
    }

    public static String leftTrim(String str, char pad) {
        char[] val = str.toCharArray();
        int len = val.length;

        int st = 0;
        while (st < len && val[st] == pad)
            st++;
        return (st > 0 && st < len) ? str.substring(st) : str;
    }
}
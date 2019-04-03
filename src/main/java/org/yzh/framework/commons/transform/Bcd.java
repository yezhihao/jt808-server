package org.yzh.framework.commons.transform;

public class Bcd {

    /**
     * 8421BCD转String
     */
    public static String encode8421String(byte[] data) {
        StringBuilder result = new StringBuilder(data.length * 2);
        for (int i = 0; i < data.length; i++) {
            result.append((data[i] & 0xf0) >>> 4);
            result.append(data[i] & 0x0f);
        }
        return result.toString();
    }

    /**
     * String转8421BCD
     */
    public static byte[] decode8421(String data) {
        if ((data.length() & 0x1) == 1)
            data = "0" + data;

        byte result[] = new byte[data.length() / 2];
        byte bytes[] = data.getBytes();
        for (int i = 0; i < result.length; i++) {

            byte high = asciiToBcd(bytes[i * 2]);
            byte low = asciiToBcd(bytes[i * 2 + 1]);

            result[i] = (byte) ((high << 4) | low);
        }
        return result;
    }

    private static byte asciiToBcd(byte asc) {
        if ((asc >= '0') && (asc <= '9'))
            return (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            return (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            return (byte) (asc - 'a' + 10);
        else
            return (byte) (asc - 48);
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
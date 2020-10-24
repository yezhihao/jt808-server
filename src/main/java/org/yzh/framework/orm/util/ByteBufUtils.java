package org.yzh.framework.orm.util;

import io.netty.buffer.ByteBuf;

/**
 * Netty ByteBuf工具类
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ByteBufUtils {

    /** 长度域占位数据块 */
    public static final byte[][] BLOCKS = new byte[][]{
            new byte[0],
            new byte[1], new byte[2],
            new byte[3], new byte[4]};


    public static int readInt(ByteBuf input, int length) {
        int value;
        switch (length) {
            case 1:
                value = input.readUnsignedByte();
                break;
            case 2:
                value = input.readUnsignedShort();
                break;
            case 3:
                value = input.readUnsignedMedium();
                break;
            case 4:
                value = input.readInt();
                break;
            default:
                throw new RuntimeException("unsupported length: " + length + " (expected: 1, 2, 3, 4)");
        }
        return value;
    }

    public static void writeInt(ByteBuf output, int length, int value) {
        switch (length) {
            case 1:
                output.writeByte(value);
                break;
            case 2:
                output.writeShort(value);
                break;
            case 3:
                output.writeMedium(value);
                break;
            case 4:
                output.writeInt(value);
                break;
            default:
                throw new RuntimeException("unsupported length: " + length + " (expected: 1, 2, 3, 4)");
        }
    }

    public static int getInt(ByteBuf output, int length, int index) {
        int value;
        switch (length) {
            case 1:
                value = output.getUnsignedByte(index);
                break;
            case 2:
                value = output.getUnsignedShort(index);
                break;
            case 3:
                value = output.getUnsignedMedium(index);
                break;
            case 4:
                value = output.getInt(index);
                break;
            default:
                throw new RuntimeException("unsupported length: " + length + " (expected: 1, 2, 3, 4)");
        }
        return value;
    }

    public static void setInt(ByteBuf output, int length, int index, int value) {
        switch (length) {
            case 1:
                output.setByte(index, value);
                break;
            case 2:
                output.setShort(index, value);
                break;
            case 3:
                output.setMedium(index, value);
                break;
            case 4:
                output.setInt(index, value);
                break;
            default:
                throw new RuntimeException("unsupported length: " + length + " (expected: 1, 2, 3, 4)");
        }
    }

    public static void writeFixedLength(ByteBuf output, int length, byte[] bytes) {
        int srcPos = length - bytes.length;
        if (srcPos > 0) {
            output.writeBytes(bytes);
            output.writeBytes(new byte[srcPos]);
        } else if (srcPos < 0) {
            output.writeBytes(bytes, -srcPos, length);
        } else {
            output.writeBytes(bytes);
        }
    }
}
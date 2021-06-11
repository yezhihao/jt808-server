package org.yzh.protocol.codec;

import org.yzh.protocol.basics.Header;

import java.util.ArrayList;
import java.util.List;


/**
 * 分包消息
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class MultiPacket {

    private final Header header;
    private int serialNo = -1;

    private int retryCount;
    private final int createTime;
    private int activeTime;

    private int count = 0;
    private final byte[][] packets;

    public MultiPacket(Header header) {
        this.header = header;
        this.createTime = (int) (System.currentTimeMillis() / 1000);
        this.activeTime = createTime;

        this.packets = new byte[header.getPackageTotal()][];
    }

    public byte[][] addAndGet(int packetNo, byte[] packetData) {
        activeTime = (int) (System.currentTimeMillis() / 1000);

        packetNo = packetNo - 1;
        if (packets[packetNo] == null) {
            packets[packetNo] = packetData;
            count++;
        }

        if (isComplete())
            return packets;
        return null;
    }

    public List<Integer> getNotArrived() {
        if (isComplete())
            return null;

        int total = packets.length;
        List<Integer> result = new ArrayList(total - count);
        for (int i = 0; i < total; i++) {
            if (packets[i] == null)
                result.add(i + 1);
        }
        return result;
    }

    public void addRetryCount(int retryCount) {
        this.retryCount += retryCount;
        this.activeTime = (int) (System.currentTimeMillis() / 1000);
    }

    public int getRetryCount() {
        return retryCount;
    }

    public int getWaitTime() {
        return (int) (System.currentTimeMillis() / 1000) - activeTime;
    }

    public int getTotalWaitTime() {
        return (int) (System.currentTimeMillis() / 1000) - createTime;
    }


    public boolean isComplete() {
        return count == packets.length;
    }

    public Header getHeader() {
        return header;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        if (serialNo == -1)
            this.serialNo = serialNo;
    }

    @Override
    public String toString() {
        int length = packets.length;
        final StringBuilder sb = new StringBuilder(82 + (length * 3));
        sb.append('[');
        sb.append("clientId=").append(header.getMobileNo());
        sb.append(", messageId=").append(Integer.toHexString(header.getMessageId()));
        sb.append(", total=").append(length);
        sb.append(", count=").append(count);
        sb.append(", retryCount=").append(retryCount);
        sb.append(", time=").append(getTotalWaitTime());
        sb.append(", packets=");
        sb.append('{');
        for (int i = 0; i < length; i++) {
            if (packets[i] != null) sb.append(i + 1);
            else sb.append(' ');
            sb.append(',');
        }
        sb.setCharAt(sb.length() - 1, '}');
        sb.append(']');
        return sb.toString();
    }
}
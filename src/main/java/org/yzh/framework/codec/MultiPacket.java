package org.yzh.framework.codec;

import java.util.ArrayList;
import java.util.List;


/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class MultiPacket {

    private final int messageId;
    private final String clientId;
    private int serialNo = -1;

    private int retryCount;
    private final int createTime;
    private int activeTime;

    private int count = 0;
    private final byte[][] packets;

    public MultiPacket(int messageId, String clientId, int total) {
        this.messageId = messageId;
        this.clientId = clientId;
        this.createTime = (int) (System.currentTimeMillis() / 1000);
        this.activeTime = createTime;

        this.packets = new byte[total][];
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

    public String getClientId() {
        return clientId;
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
        final StringBuilder b = new StringBuilder(80 + (length * 3));
        b.append('[');
        b.append("clientId=").append(clientId);
        b.append(", messageId=").append(Integer.toHexString(messageId));
        b.append(", total=").append(length);
        b.append(", count=").append(count);
        b.append(", retryCount=").append(retryCount);
        b.append(", time=").append(getTotalWaitTime());
        b.append(", packets=");
        b.append('{');
        for (int i = 0; i < length; i++) {
            if (packets[i] != null) b.append(i + 1);
            else b.append(' ');
            b.append(',');
        }
        b.setCharAt(b.length() - 1, '}');
        b.append(']');
        return b.toString();
    }
}
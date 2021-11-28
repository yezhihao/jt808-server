package org.yzh.protocol.codec;

import org.yzh.protocol.basics.JTMessage;

import java.util.ArrayList;
import java.util.List;


/**
 * 分包消息
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class MultiPacket {

    private final JTMessage firstPacket;
    private int serialNo = -1;

    private int retryCount;
    private final int createTime;
    private int activeTime;

    private int count = 0;
    private final byte[][] packets;

    public MultiPacket(JTMessage firstPacket) {
        this.firstPacket = firstPacket;
        this.createTime = (int) (System.currentTimeMillis() / 1000);
        this.activeTime = createTime;

        this.packets = new byte[firstPacket.getPackageTotal()][];
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
        List<Integer> result = new ArrayList<>(total - count);
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

    public JTMessage getFirstPacket() {
        return firstPacket;
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
        int total = packets.length;
        final StringBuilder sb = new StringBuilder(82 + (total * 3));
        sb.append('[');
        sb.append("cid=").append(firstPacket.getClientId());
        sb.append(", msg=").append(Integer.toHexString(firstPacket.getMessageId()));
        sb.append(", total=").append(total);
        sb.append(", count=").append(count);
        sb.append(", retryCount=").append(retryCount);
        sb.append(", time=").append(getTotalWaitTime());
        sb.append(", packets=");
        sb.append('{');
        for (int i = 0; i < total; i++) {
            if (packets[i] != null) sb.append(i + 1);
            else sb.append(' ');
            sb.append(',');
        }
        sb.setCharAt(sb.length() - 1, '}');
        sb.append(']');
        return sb.toString();
    }
}
package org.yzh.protocol.codec;

import io.netty.buffer.ByteBuf;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.MessageId;

import java.util.ArrayList;
import java.util.List;


/**
 * 分包消息
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class MultiPacket {

    private final JTMessage firstPacket;
    private int serialNo = -1;

    private int retryCount;
    private final long creationTime;
    private long lastAccessedTime;

    private int count = 0;
    private final ByteBuf[] packets;

    public MultiPacket(JTMessage firstPacket) {
        this.firstPacket = firstPacket;
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = creationTime;

        this.packets = new ByteBuf[firstPacket.getPackageTotal()];
    }

    public ByteBuf[] addAndGet(int packetNo, ByteBuf packetData) {
        lastAccessedTime = System.currentTimeMillis();

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

    public void release() {
        for (ByteBuf packet : packets) {
            if (packet != null)
                packet.release();
        }
    }

    public void addRetryCount(int retryCount) {
        this.retryCount += retryCount;
        this.lastAccessedTime = System.currentTimeMillis();
    }

    public int getRetryCount() {
        return retryCount;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public long getCreationTime() {
        return creationTime;
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
        sb.append(MessageId.getName(firstPacket.getMessageId()));
        sb.append(", cid=").append(firstPacket.getClientId());
        sb.append(", total=").append(total);
        sb.append(", count=").append(count);
        sb.append(", retryCount=").append(retryCount);
        sb.append(", waitTime=").append((System.currentTimeMillis() - creationTime) / 1000);
        sb.append(", packets=");
        sb.append('{');
        for (int i = 0; i < total; i++) {
            if (packets[i] != null) sb.append(i + 1);
            else sb.append(' ');
            sb.append(',');
        }
        sb.setCharAt(sb.length() - 1, '}');
        return sb.toString();
    }
}
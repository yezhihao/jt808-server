package org.yzh.framework.codec;

import java.util.ArrayList;
import java.util.List;


/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class MultiPacket {

    private int messageId;
    private String clientId;
    private int serialNo = -1;

    private int retryCount;
    private int createTime;
    private int updateTime;

    private int count = 0;
    private int total;
    private byte[][] packets;


    public MultiPacket(int messageId, String clientId, int packageTotal) {
        this.messageId = messageId;
        this.clientId = clientId;
        this.createTime = (int) (System.currentTimeMillis() / 1000);
        this.updateTime = createTime;

        this.total = packageTotal;
        this.packets = new byte[packageTotal][];
    }

    public List<Integer> getNeedPacketNo() {
        if (isComplete())
            return null;

        retryCount++;
        List<Integer> result = new ArrayList(total - count);
        for (int i = 0; i <= total; i++) {
            if (packets[i] == null)
                result.add(i + 1);
        }
        return result;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public int getWaitTime() {
        return updateTime - createTime;
    }

    public byte[][] addAndGet(int packetNo, byte[] packetData) {
        updateTime = (int) (System.currentTimeMillis() / 1000);

        packetNo = packetNo - 1;
        if (packets[packetNo] == null) {
            packets[packetNo] = packetData;
            count++;
        }

        if (isComplete())
            return packets;
        return null;
    }

    public boolean isComplete() {
        return count == total;
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

    public String toString() {
        return new StringBuilder(60)
                .append("设备号:").append(clientId)
                .append(",消息类型:").append(Integer.toHexString(messageId))
                .append(",总包数:").append(this.total)
                .append(",已收到:").append(this.count)
                .append(",重传次数:").append(this.retryCount)
                .append(",耗时:").append(getWaitTime()).append("秒").toString();
    }
}
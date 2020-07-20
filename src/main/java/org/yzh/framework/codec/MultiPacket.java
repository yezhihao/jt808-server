package org.yzh.framework.codec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
public class MultiPacket {

    private int messageId;
    private String clientId;
    private int packageTotal;

    private int retryCount;
    private int createTime;
    private int updateTime;

    private Map<Integer, byte[]> packets = new TreeMap<>();

    public MultiPacket(int messageId, String clientId, int packageTotal) {
        this.messageId = messageId;
        this.clientId = clientId;
        this.packageTotal = packageTotal;
        this.createTime = (int) (System.currentTimeMillis() / 1000);
        this.updateTime = createTime;
    }

    public List<Integer> getNeedPacketNo() {
        if (packets.size() >= packageTotal)
            return null;

        retryCount++;
        List<Integer> result = new ArrayList();
        for (int i = 1; i <= packageTotal; i++) {
            if (!packets.containsKey(i))
                result.add(i);
        }
        return result;
    }

    public byte[][] addAndGet(int packetNo, byte[] packetData) {
        updateTime = (int) (System.currentTimeMillis() / 1000);
        packets.put(packetNo, packetData);

        if (packets.size() == packageTotal) {
            byte[][] result = new byte[packageTotal][];
            int i = 0;
            for (byte[] packet : packets.values())
                result[i++] = packet;
            return result;
        }
        return null;
    }

    public String toString() {
        return new StringBuilder(60)
                .append("设备号:").append(clientId)
                .append(",消息类型:").append(Integer.toHexString(messageId))
                .append(",总包数:").append(this.packageTotal)
                .append(",已收到:").append(this.packets.size())
                .append(",重传次数:").append(this.retryCount)
                .append(",耗时:").append(updateTime - createTime).append("秒").toString();
    }
}
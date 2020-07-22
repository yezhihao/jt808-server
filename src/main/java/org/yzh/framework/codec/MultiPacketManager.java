package org.yzh.framework.codec;

import org.yzh.framework.orm.model.AbstractMessage;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public enum MultiPacketManager {

    Instance;

    private static final ConcurrentMap<String, MultiPacket> multiPacketsMap = new ConcurrentHashMap();

    public byte[][] addAndGet(AbstractMessage message, byte[] packetData) {
        String clientId = message.getClientId();
        int messageId = message.getMessageId();
        int packageTotal = message.getPackageTotal();
        int packetNo = message.getPackageNo();

        String key = new StringBuilder(18).append(clientId).append("/").append(messageId).append("/").append(packageTotal).toString();

        MultiPacket multiPacket = multiPacketsMap.get(key);
        if (multiPacket == null)
            multiPacketsMap.put(key, multiPacket = new MultiPacket(messageId, clientId, packageTotal));

        byte[][] packages = multiPacket.addAndGet(packetNo, packetData);
        if (packages == null)
            return null;
        multiPacketsMap.remove(key);
        return packages;
    }
}
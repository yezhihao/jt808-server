package org.yzh.framework.codec;

import org.yzh.framework.orm.model.AbstractHeader;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public enum MultiPacketManager {

    Instance;

    private static final ConcurrentMap<String, MultiPacket> multiPacketsMap = new ConcurrentHashMap();

    public byte[][] addAndGet(AbstractHeader message, byte[] packetData) {
        String terminalId = message.getTerminalId();
        int messageId = message.getMessageId();
        int packageTotal = message.getPackageTotal();
        int packetNo = message.getPackageNo();

        String key = new StringBuilder(18).append(terminalId).append("/").append(messageId).append("/").append(packageTotal).toString();

        MultiPacket multiPacket = multiPacketsMap.get(key);
        if (multiPacket == null)
            multiPacketsMap.put(key, multiPacket = new MultiPacket(messageId, terminalId, packageTotal));

        byte[][] packages = multiPacket.addAndGet(packetNo, packetData);
        if (packages == null)
            return null;
        multiPacketsMap.remove(key);
        return packages;
    }
}
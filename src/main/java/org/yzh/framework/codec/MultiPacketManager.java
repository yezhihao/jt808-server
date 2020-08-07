package org.yzh.framework.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.orm.model.AbstractHeader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public enum MultiPacketManager {

    Instance;

    private static final Logger log = LoggerFactory.getLogger(MultiPacketManager.class.getSimpleName());

    public static MultiPacketManager getInstance() {
        return Instance;
    }

    private static final ConcurrentHashMap<String, MultiPacket> multiPacketsMap = new ConcurrentHashMap();

//    private MultiPacketListener multiPacketListener;

    protected byte[][] addAndGet(AbstractHeader header, byte[] packetData) {
        String clientId = header.getClientId();
        int messageId = header.getMessageId();
        int packageTotal = header.getPackageTotal();
        int packetNo = header.getPackageNo();

        String key = new StringBuilder(21).append(clientId).append("/").append(messageId).append("/").append(packageTotal).toString();

        MultiPacket multiPacket = multiPacketsMap.get(key);
        if (multiPacket == null)
            multiPacketsMap.put(key, multiPacket = new MultiPacket(messageId, clientId, packageTotal));
        if (packetNo == 1)
            multiPacket.setSerialNo(header.getSerialNo());


        byte[][] packages = multiPacket.addAndGet(packetNo, packetData);
        log.info(">>>>>>分包状态{}", multiPacket);
        if (packages == null)
            return null;
        multiPacketsMap.remove(key);
        return packages;
    }
//
//    private void startListener() {
//        new Thread(() -> {
//            while (true) {
//                for (Map.Entry<String, MultiPacket> entry : multiPacketsMap.entrySet()) {
//                    String key = entry.getKey();
//
//                    Boolean keepWaiting = multiPacketListener.receiveTimeout(entry.getValue());
//                    if (!keepWaiting)
//                        multiPacketsMap.remove(key);
//                }
//                try {
//                    Thread.sleep(10000L);
//                } catch (InterruptedException e) {
//                    log.error(e.getMessage(), e);
//                }
//            }
//        }, "分包管理器").start();
//    }
//
//    public synchronized void addListener(MultiPacketListener multiPacketListener) {
//        if (this.multiPacketListener == null) {
//            this.multiPacketListener = multiPacketListener;
//            startListener();
//        }
//    }
}
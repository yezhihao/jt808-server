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

    private MultiPacketListener multiPacketListener;

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
        log.info(">>>>>>分包信息{}", multiPacket);
        if (packages == null)
            return null;
        multiPacketsMap.remove(key);
        return packages;
    }

    private void startListener() {
        new Thread(() -> {
            while (true) {
                for (Map.Entry<String, MultiPacket> entry : multiPacketsMap.entrySet()) {
                    MultiPacket packet = entry.getValue();

                    if (packet.getWaitTime() >= multiPacketListener.timeout) {
                        boolean keepWaiting = multiPacketListener.receiveTimeout(packet);
                        if (!keepWaiting) {
                            log.warn("分包接收超时 >>>>>>{}", packet);
                            multiPacketsMap.remove(entry.getKey());
                        }
                    }
                }
                try {
                    Thread.sleep(multiPacketListener.timeout * 1000 / 4);
                } catch (InterruptedException e) {
                    log.error("分包管理器", e);
                }
            }
        }, "MultiPacketManager").start();
    }

    public synchronized void addListener(MultiPacketListener multiPacketListener) {
        if (this.multiPacketListener == null) {
            this.multiPacketListener = multiPacketListener;
            startListener();
        }
    }
}
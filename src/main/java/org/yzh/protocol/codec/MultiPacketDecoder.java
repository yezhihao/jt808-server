package org.yzh.protocol.codec;

import io.github.yezhihao.netmc.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.protocol.basics.Header;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 分包消息管理
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class MultiPacketDecoder extends JTMessageDecoder {

    private static final Logger log = LoggerFactory.getLogger(MultiPacketDecoder.class.getSimpleName());

    private static final ConcurrentHashMap<String, MultiPacket> multiPacketsMap = new ConcurrentHashMap();

    private MultiPacketListener multiPacketListener;

    public MultiPacketDecoder(String basePackage) {
        this(basePackage, new MultiPacketListener(20));
    }

    public MultiPacketDecoder(String basePackage, MultiPacketListener multiPacketListener) {
        super(basePackage);
        this.multiPacketListener = multiPacketListener;
        startListener();
    }

    @Override
    protected byte[][] addAndGet(Header header, Session session, byte[] packetData) {
        String clientId = header.getMobileNo();
        int messageId = header.getMessageId();
        int packageTotal = header.getPackageTotal();
        int packetNo = header.getPackageNo();

        String key = new StringBuilder(21).append(clientId).append("/").append(messageId).append("/").append(packageTotal).toString();

        MultiPacket multiPacket = multiPacketsMap.get(key);
        if (multiPacket == null)
            multiPacketsMap.put(key, multiPacket = new MultiPacket(header, session));
        if (packetNo == 1)
            multiPacket.setSerialNo(header.getSerialNo());


        byte[][] packages = multiPacket.addAndGet(packetNo, packetData);
        log.info("<<<<<<<<<分包信息{}", multiPacket);
        if (packages == null)
            return null;
        multiPacketsMap.remove(key);
        return packages;
    }

    private void startListener() {
        Thread thread = new Thread(() -> {
            for (; ; ) {
                for (Map.Entry<String, MultiPacket> entry : multiPacketsMap.entrySet()) {
                    MultiPacket packet = entry.getValue();

                    if (packet.getWaitTime() >= multiPacketListener.timeout) {
                        boolean keepWaiting = multiPacketListener.receiveTimeout(packet);
                        if (!keepWaiting) {
                            log.warn("<<<<<<<<<分包接收超时{}", packet);
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
        }, "MultiPacketListener");
        thread.setDaemon(true);
        thread.start();
    }
}
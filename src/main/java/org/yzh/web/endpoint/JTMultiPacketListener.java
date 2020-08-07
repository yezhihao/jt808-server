//package org.yzh.web.endpoint;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.yzh.framework.codec.MultiPacket;
//import org.yzh.framework.codec.MultiPacketListener;
//import org.yzh.framework.session.MessageManager;
//import org.yzh.protocol.basics.Header;
//import org.yzh.protocol.commons.JT808;
//import org.yzh.protocol.t808.T8003;
//
//import java.util.List;
//
//public class JTMultiPacketListener implements MultiPacketListener {
//
//    private static final Logger log = LoggerFactory.getLogger(JTMultiPacketListener.class.getSimpleName());
//
//    private MessageManager messageManager = MessageManager.getInstance();
//
//    @Override
//    public boolean receiveTimeout(MultiPacket multiPacket) {
//
//        int waitTime = multiPacket.getWaitTime();
//        int retryCount = multiPacket.getRetryCount();
//        if (waitTime > 20) {
//            if (retryCount > 5) {
//
//                log.warn("收包失败，超过最大重传次数: {}", multiPacket);
//                return false;
//            } else {
//
//                T8003 request = new T8003(new Header(JT808.服务器补传分包请求, multiPacket.getClientId()));
//                request.setSerialNo(multiPacket.getSerialNo());
//                List<Integer> needPacketNo = multiPacket.getNeedPacketNo();
//                byte[] items = new byte[needPacketNo.size()];
//                for (int i = 0; i < items.length; i++) {
//                    items[i] = needPacketNo.get(i).byteValue();
//                }
//                request.setItems(items);
//                messageManager.notify(request);
//            }
//        }
//    }
//}
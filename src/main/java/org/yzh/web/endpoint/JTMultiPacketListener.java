package org.yzh.web.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.codec.MultiPacket;
import org.yzh.protocol.codec.MultiPacketListener;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T8003;

import java.util.List;

public class JTMultiPacketListener extends MultiPacketListener {

    @Autowired
    private MessageManager messageManager;

    public JTMultiPacketListener(int timeout) {
        super(timeout);
    }

    @Override
    public boolean receiveTimeout(MultiPacket multiPacket) {
        int retryCount = multiPacket.getRetryCount();
        if (retryCount > 5)
            return false;

        T8003 request = new T8003();
        request.setHeader(new Header(JT808.服务器补传分包请求).copyBy(multiPacket.getHeader()));
        request.setResponseSerialNo(multiPacket.getSerialNo());
        List<Integer> notArrived = multiPacket.getNotArrived();
        short[] idList = new short[notArrived.size()];
        for (int i = 0; i < idList.length; i++) {
            idList[i] = notArrived.get(i).shortValue();
        }
        request.setId(idList);
        if (messageManager.notify(request)) {
            multiPacket.addRetryCount(1);
            return true;
        }
        return false;
    }
}
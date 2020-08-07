package org.yzh.framework.codec;

import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T8003;

import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class MultiPacketListener {

    /**
     * 超时分包消息处理
     *
     * @param multiPacket 分包信息
     * @return 是否继续等待
     */
    public boolean receiveTimeout(MultiPacket multiPacket) {
return false;
    }

}
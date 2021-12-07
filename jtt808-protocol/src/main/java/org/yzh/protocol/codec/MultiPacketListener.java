package org.yzh.protocol.codec;

/**
 * 分包消息监听器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class MultiPacketListener {

    protected long timeout;

    public MultiPacketListener() {
        this(30);
    }

    /**
     * 超时时间 (秒)
     * @param timeout
     */
    public MultiPacketListener(int timeout) {
        this.timeout = timeout * 1000;
    }

    /**
     * 超时分包消息处理
     * @param multiPacket 分包信息
     * @return 是否继续等待
     */
    public boolean receiveTimeout(MultiPacket multiPacket) {
        return false;
    }
}
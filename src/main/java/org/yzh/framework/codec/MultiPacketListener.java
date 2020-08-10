package org.yzh.framework.codec;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class MultiPacketListener {

    protected int timeout;

    public MultiPacketListener() {
        this(30);
    }

    /**
     * 超时时间 (秒)
     * @param timeout
     */
    public MultiPacketListener(int timeout) {
        this.timeout = timeout;
    }

    /**
     * 超时分包消息处理
     * @param multiPacket 分包信息
     * @return 是否继续等待
     */
    public boolean receiveTimeout(MultiPacket multiPacket) {
        return true;
    }
}
package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息结构
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface Schema<T> {

    Logger log = LoggerFactory.getLogger(Schema.class.getSimpleName());


    /** 长度域占位数据块 */
    byte[][] BLOCKS = new byte[][]{
            new byte[0],
            new byte[1], new byte[2],
            new byte[3], new byte[4]};

    T readFrom(ByteBuf input);

    void writeTo(ByteBuf output, T message);

    default T readFrom(ByteBuf input, int length) {
        return readFrom(input);
    }

    default void writeTo(ByteBuf output, int length, T message) {
        writeTo(output, message);
    }

    default int length() {
        return 128;
    }
}
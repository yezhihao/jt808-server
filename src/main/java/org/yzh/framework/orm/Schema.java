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
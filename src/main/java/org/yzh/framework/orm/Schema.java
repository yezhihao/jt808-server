package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;

/**
 * 消息结构
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface Schema<T> {

    T readFrom(ByteBuf input);

    void writeTo(ByteBuf output, T message);

    int length();
}
package org.yzh.framework.codec;

import io.netty.buffer.ByteBuf;

/**
 * 基础消息编码
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface MessageEncoder<T> {

    ByteBuf encode(T message);

}
package org.yzh.protocol.basics;

/**
 * @author yezhihao
 * home https://gitee.com/yezhihao/jt808-server
 */
public interface JTMessageFilter<T extends JTMessage> {

    boolean doFilter(T message);
}
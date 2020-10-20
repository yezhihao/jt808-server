package org.yzh.framework.mvc.model;

import java.io.Serializable;

/**
 * 消息头
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface Header<T> extends Serializable {

    /** 客户端唯一标识 */
    T getClientId();

    /** 消息流水号 */
    int getSerialNo();

    void setSerialNo(int serialNo);
}
package org.yzh.commons.model;

/**
 * 响应状态枚举类接口
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface ResultCode {

    /**
     * 状态码
     */
    int getCode();

    /**
     * 状态信息
     */
    String getMessage();
}
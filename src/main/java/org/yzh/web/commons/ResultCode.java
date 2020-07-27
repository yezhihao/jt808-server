package org.yzh.web.commons;

/**
 * 响应状态枚举类接口
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
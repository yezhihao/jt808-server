package org.yzh.protocol.commons;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public interface ResultCode {

    int Success = 0;
    int Failure = 1;
    int MessageError = 2;
    int NotSupport = 3;
    int AlarmAck = 4;
}
package org.yzh.framework.mvc;

import org.yzh.framework.mvc.handler.Handler;

/**
 * 消息映射接口
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface HandlerMapping {

    Handler getHandler(Object messageType);

}
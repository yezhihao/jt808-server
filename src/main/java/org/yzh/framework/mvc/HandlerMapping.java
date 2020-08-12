package org.yzh.framework.mvc;

import org.yzh.framework.mvc.handler.Handler;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public interface HandlerMapping {

    Handler getHandler(int messageId);

}
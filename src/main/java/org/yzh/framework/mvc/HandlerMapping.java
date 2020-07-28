package org.yzh.framework.mvc;

import org.yzh.framework.mvc.handler.Handler;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public interface HandlerMapping {

    Handler getHandler(int messageId);

}
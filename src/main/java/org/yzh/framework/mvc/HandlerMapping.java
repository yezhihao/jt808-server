package org.yzh.framework.mvc;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public interface HandlerMapping {

    Handler getHandler(int messageId);

}
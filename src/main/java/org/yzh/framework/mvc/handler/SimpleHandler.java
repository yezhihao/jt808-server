package org.yzh.framework.mvc.handler;

import org.yzh.framework.mvc.model.Message;
import org.yzh.framework.session.Session;

import java.lang.reflect.Method;

/**
 * 同步处理
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class SimpleHandler extends Handler {

    public SimpleHandler(Object actionClass, Method actionMethod, String desc) {
        super(actionClass, actionMethod, desc);
    }

    public Message invoke(Message request, Session session) throws Exception {
        return super.invoke(request, session);
    }
}
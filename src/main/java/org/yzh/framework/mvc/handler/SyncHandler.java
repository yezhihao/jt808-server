package org.yzh.framework.mvc.handler;

import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;

import java.lang.reflect.Method;

/**
 * 同步处理
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class SyncHandler extends Handler {

    public SyncHandler(Object actionClass, Method actionMethod, String desc) {
        super(actionClass, actionMethod, desc);
    }

    public AbstractMessage invoke(AbstractMessage request, Session session) throws Exception {
        return super.invoke(request, session);
    }
}
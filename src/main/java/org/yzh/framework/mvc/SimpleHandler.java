package org.yzh.framework.mvc;

import org.yzh.framework.orm.model.AbstractMessage;

import java.lang.reflect.Method;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class SimpleHandler extends Handler {

    public SimpleHandler(Object actionClass, Method actionMethod, String desc) {
        super(actionClass, actionMethod, desc);
    }

    public <T extends AbstractMessage> T invoke(Object... args) throws Exception {
        return (T) targetMethod.invoke(targetObject, args);
    }

    @Override
    public String toString() {
        return desc;
    }
}
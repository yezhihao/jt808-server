package org.yzh.framework.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class SpringHandlerMapping extends AbstractHandlerMapping {

    @Autowired
    private ApplicationContext applicationContext;

    public Object getEndpoint(Class<?> clazz) {
        return applicationContext.getBean(clazz);
    }
}
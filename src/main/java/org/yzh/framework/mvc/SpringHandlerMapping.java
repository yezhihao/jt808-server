package org.yzh.framework.mvc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class SpringHandlerMapping extends AbstractHandlerMapping implements ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    public SpringHandlerMapping(String endpointPackage) {
        super(endpointPackage);
    }

    public Object getEndpoint(Class<?> clazz) {
        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        initial();
    }
}
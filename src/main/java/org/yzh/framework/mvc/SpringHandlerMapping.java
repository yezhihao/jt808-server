package org.yzh.framework.mvc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
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
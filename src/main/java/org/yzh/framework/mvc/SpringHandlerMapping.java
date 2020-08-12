package org.yzh.framework.mvc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.yzh.framework.mvc.annotation.Endpoint;

import java.util.Map;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class SpringHandlerMapping extends AbstractHandlerMapping implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> endpoints = applicationContext.getBeansWithAnnotation(Endpoint.class);
        for (Object bean : endpoints.values()) {
            super.registerHandlers(bean);
        }
    }
}
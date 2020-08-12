package org.yzh.framework.mvc;

import org.yzh.framework.commons.ClassUtils;
import org.yzh.framework.mvc.annotation.Endpoint;

import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DefaultHandlerMapping extends AbstractHandlerMapping {

    public DefaultHandlerMapping(String endpointPackage) {
        List<Class<?>> endpointClasses = ClassUtils.getClassList(endpointPackage, Endpoint.class);

        for (Class<?> endpointClass : endpointClasses) {
            try {
                Object bean = endpointClass.newInstance();
                super.registerHandlers(bean);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
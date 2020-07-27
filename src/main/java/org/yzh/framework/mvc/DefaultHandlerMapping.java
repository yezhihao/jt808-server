package org.yzh.framework.mvc;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class DefaultHandlerMapping extends AbstractHandlerMapping {

    public DefaultHandlerMapping(String endpointPackage) {
        super(endpointPackage);
        initial();
    }

    public Object getEndpoint(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
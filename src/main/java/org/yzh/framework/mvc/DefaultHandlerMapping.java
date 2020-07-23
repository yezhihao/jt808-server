package org.yzh.framework.mvc;

import org.yzh.framework.commons.bean.BeanUtils;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class DefaultHandlerMapping extends AbstractHandlerMapping {

    public Object getEndpoint(Class<?> clazz) {
        return BeanUtils.newInstance(clazz);
    }
}
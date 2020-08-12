package org.yzh.framework.mvc.annotation;

import java.lang.annotation.*;

/**
 * 消息接入点
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Endpoint {

}
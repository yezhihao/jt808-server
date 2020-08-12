package org.yzh.framework.mvc.annotation;

import java.lang.annotation.*;

/**
 * 消息类型映射
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mapping {

    int[] types();

    String desc() default "";

}
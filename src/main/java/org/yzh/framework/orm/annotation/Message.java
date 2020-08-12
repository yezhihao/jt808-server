package org.yzh.framework.orm.annotation;

import java.lang.annotation.*;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Message {

    int[] value() default {};

    String desc() default "";

}
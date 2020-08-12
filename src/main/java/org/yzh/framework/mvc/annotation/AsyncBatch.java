package org.yzh.framework.mvc.annotation;

import java.lang.annotation.*;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AsyncBatch {

    int poolSize() default 2;

    int maxElements() default 400;

    int maxWait() default 1000;

}
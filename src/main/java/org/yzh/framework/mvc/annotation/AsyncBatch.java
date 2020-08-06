package org.yzh.framework.mvc.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AsyncBatch {

    int poolSize() default 2;

    int maxElements() default 400;

    int maxWait() default 1000;

}
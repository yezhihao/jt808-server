package org.yzh.framework.mvc.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AsyncBatch {

    int poolSize() default 4;

    int maxElements() default 100;

    int maxWait() default 1000;

}
package org.yzh.framework.orm.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Type {

    int[] value() default {};

    String desc() default "";

}
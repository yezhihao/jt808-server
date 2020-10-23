package org.yzh.framework.orm.annotation;

import org.yzh.framework.orm.converter.Converter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Convert {

    int keySize() default 1;

    int valueSize() default 1;

    Class<? extends Converter> converter();
}
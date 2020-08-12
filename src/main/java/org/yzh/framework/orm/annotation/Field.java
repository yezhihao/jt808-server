package org.yzh.framework.orm.annotation;

import org.yzh.framework.orm.model.DataType;

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
public @interface Field {

    int index() default -1;

    String[] indexOffsetName() default "";

    int length() default -1;

    String lengthName() default "";

    DataType type() default DataType.BYTE;

    String charset() default "GBK";

    byte pad() default 0x00;

    String desc() default "";

    int[] version() default {0, 1};
}
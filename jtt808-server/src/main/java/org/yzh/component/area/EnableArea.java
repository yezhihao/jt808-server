package org.yzh.component.area;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 启用区域模块
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Import(AreaConfiguration.class)
@Documented
public @interface EnableArea {
}
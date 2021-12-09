package org.yzh.component.area;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ComponentScan({"org.yzh.component.area.controller", "org.yzh.component.area.service"})
@MapperScan("org.yzh.component.area.mapper")
@Configuration
public class AreaConfiguration {
}
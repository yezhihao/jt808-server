package org.yzh.component.area;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Configuration
@ComponentScan(basePackages = {
        "org.yzh.component.area.controller",
        "org.yzh.component.area.service"
})
@MapperScans(
        @MapperScan("org.yzh.component.area.mapper")
)
public class AreaConfiguration {

}

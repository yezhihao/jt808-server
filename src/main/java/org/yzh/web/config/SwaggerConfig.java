package org.yzh.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.yzh.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("部标JT/T 808协议快接入平台")
                .description("部标808协议快速开发包\n" +
                        "项目介绍\n" +
                        "基于netty、spring boot框架，实现了JT/T 808部标协议的服务端程序\n" +
                        "最简洁、清爽、易用的部标开发框架。\n" +
                        "\n" +
                        "技术交流QQ群：[906230542]\n" +
                        "\n" +
                        "主要特性：\n" +
                        "* 代码足够精简，便于二次开发。\n" +
                        "* 致敬Spring、Hibernate设计理念，熟悉Web开发的同学上手极快。\n" +
                        "* 使用注解描述协议，告别繁琐的封包、解包。\n" +
                        "* 支持分包请求。\n" +
                        "* 支持异步批量处理，高并发下MySQL不再是瓶颈。\n" +
                        "* 支持2013、2019两个版本的部标协议。\n" +
                        "* 内置封包&解包过程的分析工具，便于查错。\n" +
                        "* 完善的测试用例，稳定发版")
                .contact(new Contact("QQ: 1527621790", "https://gitee.com/yezhihao/jt808-server", ""))
                .termsOfServiceUrl("https://gitee.com/yezhihao/jt808-server")
                .license("")
                .licenseUrl("")
                .version("1.0.0")
                .build();
    }
}
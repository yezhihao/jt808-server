package org.yzh.web.config;

import com.google.common.base.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yzh.framework.orm.annotation.Field;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.schema.ApiModelPropertyPropertyBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.schema.Annotations.findPropertyAnnotation;

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
                .contact(new Contact("问题交流群: 906230542", "https://gitee.com/yezhihao/jt808-server", ""))
                .termsOfServiceUrl("https://gitee.com/yezhihao/jt808-server")
                .license("")
                .licenseUrl("")
                .version("1.0.0")
                .build();
    }

    @Bean
    public ApiModelPropertyPropertyBuilder customApiModelPropertyPropertyBuilder(DescriptionResolver descriptions) {
        return new ApiModelPropertyPropertyBuilder(descriptions) {
            @Override
            public void apply(ModelPropertyContext context) {
                Optional<Field> annotation = Optional.absent();
                if (context.getBeanPropertyDefinition().isPresent()) {
                    annotation = findPropertyAnnotation(context.getBeanPropertyDefinition().get(), Field.class);
                }
                if (annotation.isPresent()) {
                    String desc = annotation.get().desc();
                    if (desc.length() > 0)
                        context.getBuilder().description(desc);
                }
            }
        };
    }
}
package org.yzh.web.config;

import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import io.github.yezhihao.protostar.annotation.Field;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.PropertySpecificationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.property.ModelSpecificationFactory;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.schema.ApiModelPropertyPropertyBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

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
                .build()
                .directModelSubstitute(byte.class, int.class)
                .directModelSubstitute(byte[].class, int[].class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(LocalDateTime.class, Date.class);
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("部标JT/T 808协议快接入平台")
                .contact(new Contact("问题交流群: 906230542", "https://gitee.com/yezhihao", ""))
                .termsOfServiceUrl("https://gitee.com/yezhihao")
                .license("")
                .licenseUrl("")
                .version("1.0.0")
                .build();
    }

    @Bean
    public ApiModelPropertyPropertyBuilder customApiModelPropertyPropertyBuilder(DescriptionResolver descriptions, ModelSpecificationFactory modelSpecifications) {
        return new ApiModelPropertyPropertyBuilder(descriptions, modelSpecifications) {
            @Override
            public void apply(ModelPropertyContext context) {
                Optional<Field> annotation;
                if (context.getBeanPropertyDefinition().isPresent()) {
                    BeanPropertyDefinition beanPropertyDefinition = context.getBeanPropertyDefinition().get();
                    annotation = findPropertyAnnotation(beanPropertyDefinition, Field.class);

                    PropertySpecificationBuilder builder = context.getSpecificationBuilder();
                    String name = beanPropertyDefinition.getName();
                    if (name.equals("header")) {
                        builder.isHidden(true);
                        return;
                    }

                    if (annotation.isPresent()) {
                        Field field = annotation.get();
                        builder.position(field.index());
                        String desc = field.desc();
                        if (desc.length() > 0) {
                            builder.description(desc);
                        }
                    }
                }
            }
        };
    }
}
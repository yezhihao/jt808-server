package org.yzh.web.config;

import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Fs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.property.ModelSpecificationFactory;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.EnumTypeDeterminer;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.contexts.ParameterExpansionContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.readers.parameter.SwaggerExpandedParameterBuilder;
import springfox.documentation.swagger.schema.ApiModelPropertyPropertyBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static springfox.documentation.schema.Annotations.findPropertyAnnotation;
import static springfox.documentation.swagger.common.SwaggerPluginSupport.OAS_PLUGIN_ORDER;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.OAS_30)
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
                .title("部标JT/T808协议接入平台")
                .contact(new Contact("问题交流群: 906230542", "https://gitee.com/yezhihao", ""))
                .termsOfServiceUrl("https://gitee.com/yezhihao")
                .license("")
                .licenseUrl("")
                .version("1.0.0")
                .build();
    }

    public static final Set<String> ignores = new HashSet<>();

    static {
        ignores.add("messageId");
        ignores.add("properties");
        ignores.add("versionNo");
        ignores.add("clientId");
        ignores.add("serialNo");
        ignores.add("packageTotal");
        ignores.add("packageNo");
        ignores.add("verified");
        ignores.add("bodyLength");
        ignores.add("encryption");
        ignores.add("subpackage");
        ignores.add("version");
        ignores.add("reserved");
        ignores.add("payload");
        ignores.add("messageName");
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
                    if (ignores.contains(beanPropertyDefinition.getName())) {
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

    @Bean
    public SwaggerExpandedParameterBuilder customSwaggerExpandedParameterBuilder(DescriptionResolver descriptions, EnumTypeDeterminer enumTypeDeterminer) {
        return new SwaggerExpandedParameterBuilder(descriptions, enumTypeDeterminer) {
            @Override
            public void apply(ParameterExpansionContext context) {

                ParameterBuilder parameterBuilder = context.getParameterBuilder();
                RequestParameterBuilder requestParameterBuilder = context.getRequestParameterBuilder();

                String parentName = context.getParentName();
                boolean hidden = ignores.contains(context.getFieldName()) || "session".equals(parentName);

                parameterBuilder.hidden(hidden);
                requestParameterBuilder.hidden(hidden);

                if (!hidden) {
                    Field field = getField(context);

                    if (field != null) {
                        parameterBuilder
                                .description(field.desc())
                                .required(true)
                                .order(OAS_PLUGIN_ORDER);

                        requestParameterBuilder
                                .description(field.desc())
                                .required(true)
                                .parameterIndex(field.index() + 10)
                                .precedence(OAS_PLUGIN_ORDER);
                    }
                }
            }
        };
    }

    private Field getField(ParameterExpansionContext context) {
        Optional<Field> optionalField = context.findAnnotation(Field.class);
        if (optionalField.isPresent())
            return optionalField.get();
        Optional<Fs> optionalFs = context.findAnnotation(Fs.class);
        if (optionalFs.isPresent())
            return optionalFs.get().value()[0];
        return null;
    }
}
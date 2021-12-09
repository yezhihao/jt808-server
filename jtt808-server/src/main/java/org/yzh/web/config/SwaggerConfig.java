package org.yzh.web.config;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Fs;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static springfox.documentation.schema.Annotations.findPropertyAnnotation;
import static springfox.documentation.swagger.common.SwaggerPluginSupport.OAS_PLUGIN_ORDER;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Configuration
public class SwaggerConfig {

    @Autowired
    private TypeResolver resolver;

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(byte.class, int.class)
                .directModelSubstitute(byte[].class, int[].class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(LocalDateTime.class, Date.class)
                .alternateTypeRules(
                        AlternateTypeRules.newRule(resolver.resolve(Mono.class, WildcardType.class), resolver.resolve(WildcardType.class)),
                        AlternateTypeRules.newRule(resolver.resolve(Flux.class, WildcardType.class), resolver.resolve(List.class, WildcardType.class))
                )
                .apiInfo(apiInfo());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("部标JT/T808协议接入平台")
                .contact(new Contact("问题交流群：906230542", "https://gitee.com/yezhihao", ""))
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
        ignores.add("protocolVersion");
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
        ignores.add("session");
        ignores.add("messageName");
    }

    @Bean
    public ApiModelPropertyPropertyBuilder customApiModelPropertyPropertyBuilder(DescriptionResolver descriptions, ModelSpecificationFactory modelSpecifications) {
        return new ApiModelPropertyPropertyBuilder(descriptions, modelSpecifications) {
            @Override
            public void apply(ModelPropertyContext context) {
                context.getBeanPropertyDefinition().ifPresent(definition -> {

                    if (ignores.contains(definition.getName())) {
                        context.getSpecificationBuilder().isHidden(true);

                    } else {
                        Field field = getField(definition);
                        if (field != null) {
                            context.getSpecificationBuilder()
                                    .position(field.index())
                                    .description(field.desc());
                        } else {
                            findPropertyAnnotation(definition, Schema.class).ifPresent(schema -> {
                                context.getSpecificationBuilder()
                                        .isHidden(schema.hidden())
                                        .required(schema.required())
                                        .position(schema.maxProperties())
                                        .description(schema.description());
                            });
                        }
                    }
                });
            }
        };
    }

    @Bean
    public SwaggerExpandedParameterBuilder customSwaggerExpandedParameterBuilder(DescriptionResolver descriptions, EnumTypeDeterminer enumTypeDeterminer) {
        return new SwaggerExpandedParameterBuilder(descriptions, enumTypeDeterminer) {
            @Override
            public void apply(ParameterExpansionContext context) {
                if (ignores.contains(context.getFieldName()) || ignores.contains(context.getParentName())) {
                    context.getParameterBuilder().hidden(true);
                    context.getRequestParameterBuilder().hidden(true);

                } else {
                    Field field = getField(context);
                    if (field != null) {
                        context.getParameterBuilder()
                                .description(field.desc())
                                .required(true)
                                .order(OAS_PLUGIN_ORDER);

                        context.getRequestParameterBuilder()
                                .description(field.desc())
                                .required(true)
                                .parameterIndex(field.index() + 10)
                                .precedence(OAS_PLUGIN_ORDER);
                    } else {
                        context.findAnnotation(Schema.class).ifPresent(schema -> {
                            context.getParameterBuilder()
                                    .hidden(schema.hidden())
                                    .required(schema.required())
                                    .description(schema.description())
                                    .order(OAS_PLUGIN_ORDER);

                            context.getRequestParameterBuilder()
                                    .hidden(schema.hidden())
                                    .required(schema.required())
                                    .description(schema.description())
                                    .parameterIndex(schema.maxProperties())
                                    .precedence(OAS_PLUGIN_ORDER);
                        });
                    }
                }
            }
        };
    }

    private Field getField(BeanPropertyDefinition definition) {
        Optional<Field> optionalField = findPropertyAnnotation(definition, Field.class);
        if (optionalField.isPresent())
            return optionalField.get();
        Optional<Fs> optionalFs = findPropertyAnnotation(definition, Fs.class);
        if (optionalFs.isPresent())
            return optionalFs.get().value()[0];
        return null;
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
package org.yzh.web.config;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Fs;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI OpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("部标JT/T808协议接入平台")
                        .contact(new Contact().name("问题交流群：906230542").url("https://gitee.com/yezhihao"))
                        .license(new License().name("Apache-2.0").url("https://www.apache.org/licenses"))
                        .version("1.0.0"));
    }

    public static final Set<String> ignores = new HashSet<>();

    static {
        ignores.add("messageId");
        ignores.add("properties");
        ignores.add("protocolVersion");
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
    public PropertyCustomizer propertyCustomizer() {
        return (schema, type) -> {
            String propertyName = type.getPropertyName();
            boolean readOnly = ignores.contains(propertyName);
            schema.readOnly(readOnly);

            Field field = getField(type.getCtxAnnotations());
            if (field != null) {
                schema.description(field.desc());
                if (!readOnly) {
                    schema.addRequiredItem("true");
                }
            }
            return schema;
        };
    }

    private Field getField(Annotation[] annotations) {
        if (annotations != null) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof Field) {
                    return (Field) annotation;
                }
                if (annotation instanceof Fs) {
                    return ((Fs) annotation).value()[0];
                }
            }
        }
        return null;
    }
}
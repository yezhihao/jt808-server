package org.yzh.commons.spring;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import jakarta.servlet.ServletException;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.context.request.WebRequest;
import org.yzh.commons.model.APICodes;
import org.yzh.commons.util.DateUtils;
import org.yzh.commons.util.Exceptions;
import org.yzh.commons.util.JsonUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class BaseConfig {

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        ObjectMapper objectMapper = applicationContext.getBean(ObjectMapper.class);
        try {
            JsonUtils.setObjectMapper(objectMapper);
        } catch (Throwable ignored) {
        }
    }

    @Bean
    public DefaultErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                Throwable error = super.getError(webRequest);
                if (error != null) {
                    while (error instanceof ServletException && error.getCause() != null) {
                        error = error.getCause();
                    }
                }
                Map<String, Object> result = new LinkedHashMap<>(4);
                result.put("code", APICodes.InternalError.getCode());
                result.put("msg", APICodes.InternalError.getMessage());
                result.put("details", Exceptions.getStackTrace(error));
                return result;
            }
        };
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeJackson2ObjectMapper() {
        return builder -> {
            builder.modules(objectMapperModules());
            builder.postConfigurer(BaseConfig::configureObjectMapper);
        };
    }

    public static void configureObjectMapper(ObjectMapper mapper) {
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, true);//忽略循环引用
        mapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true);//循环引用写入null
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);// 兼容高德地图api
    }

    public static SimpleModule[] objectMapperModules() {
        SimpleModule longModule = new SimpleModule();
        longModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        longModule.addSerializer(Long.class, ToStringSerializer.instance);

        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateUtils.DATE_TIME_FORMATTER));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateUtils.DATE_TIME_FORMATTER));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));

        return Arrays.asList(longModule, timeModule).toArray(new SimpleModule[0]);
    }
}
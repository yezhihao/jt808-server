package org.yzh.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.yzh.commons.util.DateUtils;
import org.yzh.commons.util.JsonUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Configuration
public class BeanConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(500L)
                .expireAfterWrite(30, TimeUnit.MINUTES));
        return manager;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonUtils.mapper;
    }

    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String s) {
                return LocalDateTime.parse(s, DateUtils.DATE_TIME_FORMATTER);
            }
        };
    }

    @Bean
    public Converter<String, LocalDate> localDateConverter() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String s) {
                return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            }
        };
    }

    @Bean
    public Converter<String, LocalTime> localTimeConverter() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String s) {
                return LocalTime.parse(s, DateTimeFormatter.ISO_LOCAL_TIME);
            }
        };
    }
}
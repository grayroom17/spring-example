package com.example.logging.config;

import com.example.logging.aop.AroundPointcut;
import com.example.logging.aop.CommonPointcuts;
import com.example.logging.aop.FirstAspect;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Slf4j
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnClass(LoggingProperties.class)
@ConditionalOnProperty(prefix = "app.common.logging", name = "enabled", havingValue = "true")
public class LoggingAutoconfiguration {

    @PostConstruct
    void init() {
        log.info("LoggingAutoConfiguration initialized");
    }

    @Bean
    public CommonPointcuts commonPointcuts() {
        return new CommonPointcuts();
    }

    @Bean
    @Order(1)
    public FirstAspect firstAspect() {
        return new FirstAspect();
    }

    @Bean
    @Order(2)
    public AroundPointcut aroundPointcut() {
        return new AroundPointcut();
    }

}

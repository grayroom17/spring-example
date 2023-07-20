package com.example.spring.config;

import com.example.spring.config.condition.JpaCondition;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Conditional(JpaCondition.class)
@Configuration
public class JpaConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "db")
    DatabaseProperties databaseProperties() {
        return new DatabaseProperties();
    }

    @PostConstruct
    void postConstruct() {
        log.info("Jpa configuration is enabled");
    }

}

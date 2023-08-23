package com.example.logging.config;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@Slf4j
@ConfigurationProperties(prefix = "app.common.logging")
public class LoggingProperties {

    /**
     * to enable common logging aop on service layer
     */
    boolean enabled;

    /**
     * set logging level
     */
    String level;

    @PostConstruct
    void init() {
        log.info("Logging properties initialized: {}", this);
    }
}

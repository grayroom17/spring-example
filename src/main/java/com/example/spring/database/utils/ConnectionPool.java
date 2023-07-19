package com.example.spring.database.utils;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
@Component("pool1")
public class ConnectionPool {

    @Value("${db.username}")
    String username;
    @Value("${db.pool.size}")
    Integer poolSize;
    /*List<Object> args;
    @NonFinal
    @Setter
    private Map<String, Object> properties;*/

    @PostConstruct
    private void init() {
        log.info("ConnectionPool preConstruct callback");
    }

    @PreDestroy
    private void preDestroy() {
        log.info("ConnectionPool postDestroy callback");
    }
}

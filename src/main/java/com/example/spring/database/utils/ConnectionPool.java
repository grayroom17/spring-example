package com.example.spring.database.utils;

import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConnectionPool implements InitializingBean, DisposableBean {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ConnectionPool.class);
    private final String username;
    private final Integer poolSize;
    private final List<Object> args;
    @NonFinal
    private Map<String, Object> properties;

    public ConnectionPool(String username, Integer poolSize, List<Object> args) {
        this.username = username;
        this.poolSize = poolSize;
        this.args = args;
    }

    public ConnectionPool(String username, Integer poolSize, List<Object> args, Map<String, Object> properties) {
        this.username = username;
        this.poolSize = poolSize;
        this.args = args;
        this.properties = properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet callback");
    }

    private void init() {
        log.info("ConnectionPool preConstruct callback");
    }

    @Override
    public void destroy() throws Exception {
        log.info("on destruction callback");
    }

    private void preDestroy() {
        log.info("ConnectionPool postDestroy callback");
    }
}

package com.example.spring.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
public class DatabaseProperties {
    String username;
    String password;
    String driver;
    String url;
    String hosts;
    PoolProperties pool;
    List<PoolProperties> pools;
    Map<String, Object> properties;

    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Data
    @NoArgsConstructor
    public static class PoolProperties {
        Integer size;
        Integer timeout;
    }

}

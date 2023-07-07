package com.example.spring.database.utils;

import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ConnectionPool {
    String username;
    Integer poolSize;
    List<Object> args;
    Map<String, Object> properties;
}

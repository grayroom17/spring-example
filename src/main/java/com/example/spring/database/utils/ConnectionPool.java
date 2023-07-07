package com.example.spring.database.utils;

import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class ConnectionPool {
    String username;
    Integer poolSize;
    List<Object> args;
    @Setter
    @NonFinal
    Map<String, Object> properties;
}

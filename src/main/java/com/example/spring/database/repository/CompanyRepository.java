package com.example.spring.database.repository;

import com.example.spring.database.utils.ConnectionPool;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CompanyRepository {

    ConnectionPool connectionPool;

    public static CompanyRepository of(ConnectionPool connectionPool) {
        return new CompanyRepository(connectionPool);
    }
}

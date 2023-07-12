package com.example.spring.database.repository;

import com.example.spring.database.utils.ConnectionPool;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Repository
public class CompanyRepository {

    @Qualifier("pool2")
    ConnectionPool connectionPool;

}

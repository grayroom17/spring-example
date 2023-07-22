package com.example.spring.it;

import com.example.spring.database.utils.ConnectionPool;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;

@TestConfiguration
public class TestApplicationRunner {

    @SpyBean(name = "pool1")
    ConnectionPool pool1;

}

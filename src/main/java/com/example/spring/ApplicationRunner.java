package com.example.spring;

import com.example.spring.database.utils.ConnectionPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class ApplicationRunner {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        ConnectionPool connectionPool = context.getBean("pool1", ConnectionPool.class);
        log.info(">>>>>>>>> " + connectionPool + " <<<<<<<<<<<");
    }

}

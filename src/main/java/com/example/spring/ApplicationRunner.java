package com.example.spring;

import com.example.spring.database.repository.CompanyRepository;
import com.example.spring.database.repository.CrudRepository;
import com.example.spring.database.utils.ConnectionPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class ApplicationRunner {

    private static String formattedString = ">>>>>>>>> %s <<<<<<<<<<<";

    public static void main(String[] args) {
        CompanyRepository companyRepository;
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml")) {

            ConnectionPool singletonConnectionPool1 = context.getBean("pool1", ConnectionPool.class);
            ConnectionPool singletonConnectionPool2 = context.getBean("pool1", ConnectionPool.class);
            log.info(formattedString.formatted(singletonConnectionPool1));
            log.info(formattedString.formatted(singletonConnectionPool2));

            ConnectionPool prototypeConnectionPool1 = context.getBean("pool2", ConnectionPool.class);
            ConnectionPool prototypeConnectionPool2 = context.getBean("pool2", ConnectionPool.class);
            log.info(formattedString.formatted(prototypeConnectionPool1));
            log.info(formattedString.formatted(prototypeConnectionPool2));

            companyRepository = context.getBean("companyRepository2", CompanyRepository.class);
            log.info(formattedString.formatted(companyRepository));

            CrudRepository departmentRepository = context.getBean("departmentRepository", CrudRepository.class);
            log.info(formattedString.formatted(departmentRepository));
            log.info(departmentRepository.findById(1).toString());
        }
    }

}

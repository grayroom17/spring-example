package com.example.spring;

import com.example.spring.config.ApplicationConfiguration;
import com.example.spring.database.repository.CompanyRepository;
import com.example.spring.database.utils.ConnectionPool;
import com.example.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class ApplicationRunner {

    private static String formattedString = ">>>>>>>>> %s <<<<<<<<<<<";

    public static void main(String[] args) {
        CompanyRepository companyRepository;
//        переход на AnnotationConfigApplicationContext
//        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml")) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {

            ConnectionPool singletonConnectionPool1 = context.getBean("pool2", ConnectionPool.class);
            log.info(formattedString.formatted(singletonConnectionPool1));

            companyRepository = context.getBean("companyRepository", CompanyRepository.class);
            log.info(formattedString.formatted(companyRepository));

            UserService userService = context.getBean("userService", UserService.class);
            log.info(formattedString.formatted(userService));
        }
    }

}

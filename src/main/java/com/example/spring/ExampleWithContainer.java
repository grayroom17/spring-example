package com.example.spring;

import com.example.spring.ioc.Container;
import com.example.spring.service.UserService;

public class ExampleWithContainer {

    public static void main(String[] args) {

//        ConnectionPool connectionPool = new ConnectionPool();
//        UserRepository userRepository = new UserRepository(connectionPool);
//        CompanyRepository companyRepository = new CompanyRepository(connectionPool);
//        UserService userService = new UserService(userRepository, companyRepository);

        Container container = new Container();
        UserService userService = container.get(UserService.class);
    }

}

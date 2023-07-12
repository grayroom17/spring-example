package com.example.spring.config;

import com.example.spring.database.repository.CrudRepository;
import com.example.spring.database.repository.UserRepository;
import com.example.spring.database.utils.ConnectionPool;
import com.example.web.config.WebConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Component;

import static org.springframework.context.annotation.FilterType.*;

//аннотацией @ImportResource можно добавить конфигурацию из xml файла
//Таким образом можно создать комбинированную конфигурацию(java based + xml based)
//@ImportResource("classpath:application.xml")

//аннотация @Import используется, чтобы подтянуть конфигурационные
//классы из других пакетов(даже тех, которые не сканируются), модулей и т.д.
//Таким образом создается комбинированная конфигурация из нескольких конфигурационных java классов
@Import(WebConfiguration.class)

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(
        basePackages = "com.example.spring",
        useDefaultFilters = false,
        includeFilters = {
                @Filter(type = ANNOTATION, value = Component.class),
                @Filter(type = ASSIGNABLE_TYPE, value = CrudRepository.class),
                @Filter(type = REGEX, pattern = "com\\..+Repository")
        })
public class ApplicationConfiguration {

    @Bean
    public ConnectionPool pool2(@Value("${db.username}") String username) {
        return new ConnectionPool(username, 20);
    }

    @Bean
    public UserRepository userRepository2(ConnectionPool pool2) {
        return new UserRepository(pool2);
    }

}

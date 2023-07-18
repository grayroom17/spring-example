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

//создает оригинальные объекты бинов
//@Configuration(proxyBeanMethods = false)

// По умолчанию при создании бины оборачиваются в прокси объекты, что позволяет делать вызов конфигурационного метода бина
//несколько раз. Первый раз прокси инициализирует объект, а последующие разы просто возвращает его. См. userRepository2()
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

    @Bean("pool2")
    public ConnectionPool connectionPool(@Value("${db.username}") String username) {
        return new ConnectionPool(username, 20);
    }

    @Bean
    public ConnectionPool pool3() {
        return new ConnectionPool("test-user", 25);
    }

    //бин будет загружен в контекст, только когда активным профилем будет НЕ prod
    //@Profile("!prod")

    //бин будет загружен в контекст, только когда активным профилем будет prod или web
    //@Profile("prod | web")

    //бин будет загружен в контекст, только когда активным профилем будет prod
    @Profile("prod")
    @Bean
    public UserRepository userRepository2(ConnectionPool pool2) {
        return new UserRepository(pool2);
    }

    @Bean
    public UserRepository userRepository4() {
        ConnectionPool pool1 = pool3();
        ConnectionPool pool2 = pool3();
        ConnectionPool pool3 = pool3();
        return new UserRepository(pool3());
    }

    //бин будет загружен в контекст, только когда активными профилями будут prod и web
    @Profile("prod & web")
    @Bean
    public UserRepository userRepository3(ConnectionPool pool2) {
        return new UserRepository(pool2);
    }

}

package com.example.spring.config;

import com.example.spring.database.utils.ConnectionPool;
import com.example.web.config.WebConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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

//@PropertySource не нужен, т.к @SpringBootApplication загрузит все properties(или yaml) файлы, которые начинаются с application
// и лежат в том же пакете что и @SpringBootApplication.
//@PropertySource("classpath:application.properties")

//@ComponentScan не нужен после переезда на Spring Boot, т.к. @SpringBootApplication содержит @ComponentScan
// и сканирует пакет в котором находится.
/*@ComponentScan(
        basePackages = "com.example.spring",
        useDefaultFilters = false,
        includeFilters = {
                @Filter(type = ANNOTATION, value = Component.class),
                @Filter(type = ASSIGNABLE_TYPE, value = CrudRepository.class),
                @Filter(type = REGEX, pattern = "com\\..+Repository")
        })*/
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

}

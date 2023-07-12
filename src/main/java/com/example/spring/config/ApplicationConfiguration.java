package com.example.spring.config;

import com.example.spring.database.repository.CrudRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import static org.springframework.context.annotation.FilterType.*;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(
        basePackages = "com.example.spring",
        useDefaultFilters = false,
        includeFilters = {
                @Filter(
                        type = ANNOTATION,
                        value = Component.class
                ),
                @Filter(
                        type = ASSIGNABLE_TYPE,
                        value = CrudRepository.class
                ),
                @Filter(
                        type = REGEX,
                        pattern = "com\\..+Repository"
                )
        })
public class ApplicationConfiguration {
}

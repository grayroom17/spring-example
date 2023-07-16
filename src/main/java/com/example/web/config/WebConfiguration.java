package com.example.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//данная конфигурация будет загружена, только тогда когда будет передана метка web
@Profile("web")
@Configuration
public class WebConfiguration {
}

package com.example.spring.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/messages")
public class MessageRestController {

    MessageSource messageSource;

    @GetMapping
    public String getMessages(@RequestParam("key") String key,
                              @RequestParam("lang") String language) {
        return messageSource.getMessage(key, null, null, new Locale(language));
    }

}

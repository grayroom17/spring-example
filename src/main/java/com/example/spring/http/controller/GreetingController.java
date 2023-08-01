package com.example.spring.http.controller;

import com.example.spring.dto.UserReadDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/v1")
@SessionAttributes({"user"})
public class GreetingController {

    //    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @GetMapping("/hello/{id}")
    public ModelAndView hello(ModelAndView modelAndView,
                              @RequestParam("age") Integer age,
                              @RequestParam String sex,
                              @RequestHeader("accept") String accept,
                              @CookieValue("JSESSIONID") String jsessionId,
                              @PathVariable Integer id) {
        modelAndView.setViewName("greeting/hello");
        return modelAndView;
    }

    //    @RequestMapping(value = "/bye",method = RequestMethod.GET)
    @GetMapping("/bye")
    public ModelAndView bye(@SessionAttribute("user") UserReadDto user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting/bye");

        return modelAndView;
    }

    @GetMapping("/hello")
    public ModelAndView hello2(ModelAndView modelAndView,
                               HttpServletRequest request) {
        modelAndView.setViewName("greeting/hello");
        modelAndView.addObject("user", new UserReadDto(1L, "Ivan"));

        return modelAndView;
    }

}

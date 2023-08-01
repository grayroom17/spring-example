package com.example.spring.http.controller;

import com.example.spring.dto.UserReadDto;
import com.example.spring.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
@SessionAttributes({"user"})
public class GreetingController {

    @GetMapping("/hello")
    public String hello2(Model model,
                         HttpServletRequest request,
                         @ModelAttribute UserReadDto userReadDto) {
        model.addAttribute("user", new UserReadDto(1L, "Ivan"));
        return "greeting/hello";
    }

    @GetMapping("/bye")
    public String bye(@SessionAttribute("user") UserReadDto user, Model model) {
        return "greeting/bye";
    }

    @ModelAttribute("roles")
    public List<Role> roles() {
        return Arrays.asList(Role.values());
    }

}

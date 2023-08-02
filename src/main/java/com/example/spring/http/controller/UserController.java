package com.example.spring.http.controller;

import com.example.spring.dto.UserCreateEditDto;
import com.example.spring.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @GetMapping
    public String findAll(Model model) {
//        model.addAttribute("users", userService.findAll());
//        model.addAttribute("users", userService.findAll(filter));
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id,
                           Model model) {
        //        model.addAttribute("user", userService.findById());
        return "users/user";
    }

    @PostMapping
    public String create(@ModelAttribute UserCreateEditDto dto) {
//        userService.create(dto);
        return "redirect:users/" + 25;
    }

    //    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute UserCreateEditDto dto) {
//        userService.update(id, dto);
        return "redirect:users/{id}";
    }

    //    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
//        userService.delete(id);
        return "redirect:/users";
    }

}

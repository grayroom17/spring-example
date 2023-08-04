package com.example.spring.it.http.controller;

import com.example.spring.it.BaseIT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.spring.dto.UserCreateEditDto.Fields.*;
import static com.example.spring.entity.Role.USER;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@AutoConfigureMockMvc
class UserControllerIT extends BaseIT {

    MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(5)));
    }

    @Test
    void create() throws Exception {


        mockMvc.perform(post("/users")
                        .param(username, "test@gmail.com")
                        .param(firstname, "test")
                        .param(lastname, "testTest")
                        .param(role, USER.name())
                        .param(companyId, "1")
                        .param(birthDate, "2000-01-01")
                )
                .andExpectAll(
                        status().is3xxRedirection()
//                        , redirectedUrlPattern("/users/{\\d+}")
                );
    }

}
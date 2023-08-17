package com.example.spring.it.http.controller;

import com.example.spring.it.BaseIT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.spring.dto.UserCreateEditDto.Fields.*;
import static com.example.spring.entity.Role.USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@AutoConfigureMockMvc
class UserControllerIT extends BaseIT {

    MockMvc mockMvc;

//    @BeforeEach
//    void init() {
//        List<GrantedAuthority> roles = Arrays.asList(ADMIN, USER);
//        User testUser = new User("test@gmail.com", "test", roles);
//        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(testUser, testUser.getPassword(), roles);
//
//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        securityContext.setAuthentication(authenticationToken);
//        SecurityContextHolder.setContext(securityContext);
//    }

    @Test
    @WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
    void findAll() throws Exception {
        mockMvc.perform(get("/users")
                        /*.with(user("test@gmail.com")
                                .password("test")
                                .authorities(ADMIN, USER))*/)
                .andExpect(status().isOk())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"));
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
                        , redirectedUrlPattern("users/{\\d+}")
                );
    }

}
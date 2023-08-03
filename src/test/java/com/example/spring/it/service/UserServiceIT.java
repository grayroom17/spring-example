package com.example.spring.it.service;

import com.example.spring.dto.UserCreateEditDto;
import com.example.spring.dto.UserReadDto;
import com.example.spring.entity.Role;
import com.example.spring.it.BaseIT;
import com.example.spring.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class UserServiceIT extends BaseIT {

    UserService userService;


    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(5);
    }

    @Test
    void findById() {
        UserReadDto user = userService.findById(1L).orElseThrow();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstname()).isEqualTo("Ivan");
        assertThat(user.getLastname()).isEqualTo("Ivanov");
        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
        assertThat(user.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 10));
        assertThat(user.getUsername()).isEqualTo("ivan@gmail.com");
        assertThat(user.getCompanyId()).isEqualTo(1);
    }

    @Test
    void create() {
        UserCreateEditDto dto = UserCreateEditDto.builder()
                .username("test@gmail.com")
                .firstname("test")
                .lastname("test")
                .companyId(1)
                .role(Role.USER)
                .birthDate(LocalDate.of(1999, 9, 9))
                .build();

        UserReadDto userReadDto = userService.create(dto);

        assertThat(userReadDto).isNotNull();
        assertThat(userReadDto.getId()).isNotNull();
        assertThat(userReadDto.getFirstname()).isEqualTo(dto.getFirstname());
        assertThat(userReadDto.getLastname()).isEqualTo(dto.getLastname());
        assertThat(userReadDto.getRole()).isEqualTo(dto.getRole());
        assertThat(userReadDto.getBirthDate()).isEqualTo(dto.getBirthDate());
        assertThat(userReadDto.getUsername()).isEqualTo(dto.getUsername());
        assertThat(userReadDto.getCompanyId()).isEqualTo(dto.getCompanyId());
    }

    @Test
    void update() {
        UserCreateEditDto dto = UserCreateEditDto.builder()
                .username("test@gmail.com")
                .firstname("test")
                .lastname("test")
                .companyId(1)
                .role(Role.USER)
                .birthDate(LocalDate.of(1999, 9, 9))
                .build();

        UserReadDto user = userService.update(1L, dto).orElseThrow();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstname()).isEqualTo(dto.getFirstname());
        assertThat(user.getLastname()).isEqualTo(dto.getLastname());
        assertThat(user.getRole()).isEqualTo(dto.getRole());
        assertThat(user.getBirthDate()).isEqualTo(dto.getBirthDate());
        assertThat(user.getUsername()).isEqualTo(dto.getUsername());
        assertThat(user.getCompanyId()).isEqualTo(dto.getCompanyId());
    }

    @Test
    void delete() {
        assertThat(userService.delete(1L)).isTrue();
    }
}
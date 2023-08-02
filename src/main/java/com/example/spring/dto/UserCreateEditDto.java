package com.example.spring.dto;

import com.example.spring.entity.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserCreateEditDto {

    String username;

    LocalDate birthDate;

    String firstname;

    String lastname;

    Role role;

    Integer companyId;

}

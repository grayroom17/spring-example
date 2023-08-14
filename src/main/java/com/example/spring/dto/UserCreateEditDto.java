package com.example.spring.dto;

import com.example.spring.entity.Role;
import com.example.spring.validation.UserInfo;
import com.example.spring.validation.group.CreateMarker;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Value
@Builder
@FieldNameConstants
@UserInfo(groups = CreateMarker.class)
public class UserCreateEditDto {

    @Email
    String username;

    @NotBlank(groups = CreateMarker.class)
    String rawPassword;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @Size(min = 3, max = 64)
    String firstname;

    String lastname;

    Role role;

    Integer companyId;

    MultipartFile image;

}

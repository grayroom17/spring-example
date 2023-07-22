package com.example.spring.it.service;

import com.example.spring.config.DatabasePropertiesWithConfigurationProperties;
import com.example.spring.dto.DepartmentReadDto;
import com.example.spring.it.BaseIT;
import com.example.spring.service.DepartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class DepartmentServiceIT extends BaseIT {
    static Integer COMPANY_ID = 1;

    DepartmentService departmentService;
    DatabasePropertiesWithConfigurationProperties databaseProperties;

    @Test
    void findById() {

        Optional<DepartmentReadDto> optional = departmentService.findById(COMPANY_ID);
        DepartmentReadDto expected = new DepartmentReadDto(COMPANY_ID);

        assertTrue(optional.isPresent());
        optional.ifPresent(actual -> assertEquals(expected, actual));

    }


}

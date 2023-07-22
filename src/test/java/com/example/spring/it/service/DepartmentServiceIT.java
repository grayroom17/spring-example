package com.example.spring.it.service;

import com.example.spring.dto.DepartmentReadDto;
import com.example.spring.service.DepartmentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FieldDefaults(level = AccessLevel.PRIVATE)
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = ApplicationRunner.class, initializers = ConfigDataApplicationContextInitializer.class)
@SpringBootTest
class DepartmentServiceIT {

    static final Integer COMPANY_ID = 1;

    @Autowired
    DepartmentService departmentService;

    @Test
    void findById() {

        Optional<DepartmentReadDto> optional = departmentService.findById(COMPANY_ID);
        DepartmentReadDto expected = new DepartmentReadDto(COMPANY_ID);

        assertTrue(optional.isPresent());
        optional.ifPresent(actual -> assertEquals(expected, actual));

    }


}

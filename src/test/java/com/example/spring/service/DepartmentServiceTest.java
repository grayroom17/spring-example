package com.example.spring.service;

import com.example.spring.database.repository.CrudRepository;
import com.example.spring.dto.DepartmentReadDto;
import com.example.spring.entity.Department;
import com.example.spring.listener.entity.EntityEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    private static final Integer COMPANY_ID = 1;

    @Mock
    CrudRepository<Integer, Department> departmentRepository;
    @Mock
    ApplicationEventPublisher eventPublisher;
    @InjectMocks
    DepartmentService departmentService;


    @Test
    void findById() {

        doReturn(Optional.of(new Department(COMPANY_ID)))
                .when(departmentRepository).findById(COMPANY_ID);

        Optional<DepartmentReadDto> optional = departmentService.findById(COMPANY_ID);
        DepartmentReadDto expected = new DepartmentReadDto(COMPANY_ID);

        assertTrue(optional.isPresent());
        optional.ifPresent(actual -> assertEquals(expected, actual));

        verify(eventPublisher).publishEvent(any(EntityEvent.class));
        verifyNoMoreInteractions(eventPublisher);
    }


}
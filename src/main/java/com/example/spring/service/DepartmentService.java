package com.example.spring.service;

import com.example.spring.database.repository.CrudRepository;
import com.example.spring.dto.DepartmentReadDto;
import com.example.spring.entity.Department;
import com.example.spring.listener.entity.AccessType;
import com.example.spring.listener.entity.EntityEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DepartmentService {

    CrudRepository<Integer, Department> departmentRepository;
    ApplicationEventPublisher applicationEventPublisher;

    public Optional<DepartmentReadDto> findById(Integer id) {
        return departmentRepository.findById(id)
                .map(entity -> {
                    applicationEventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return new DepartmentReadDto(entity.id());
                });
    }

}

package com.example.spring.database.repository;

import com.example.spring.bpp.Auditing;
import com.example.spring.bpp.InjectBean;
import com.example.spring.bpp.Transaction;
import com.example.spring.entity.Department;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transaction
@Auditing
public class DepartmentRepository implements CrudRepository<Integer, Department> {
    @InjectBean
    UserRepository userRepository;

    @Override
    public Optional<Department> findById(Integer id) {
        log.info("findById method...");
        return Optional.of(new Department(id));
    }

    @Override
    public void delete(Department entity) {
        log.info("delete method...");
    }

    @PostConstruct
    private void postConstruct(){
        log.info("postConstruct method...");
    }
}

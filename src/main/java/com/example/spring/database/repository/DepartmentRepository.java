package com.example.spring.database.repository;

import com.example.spring.bpp.Auditing;
import com.example.spring.bpp.Transaction;
import com.example.spring.database.utils.ConnectionPool;
import com.example.spring.entity.Department;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transaction
@Auditing
@Scope(SCOPE_PROTOTYPE)
@Repository
public class DepartmentRepository implements CrudRepository<Integer, Department> {

    UserRepository userRepository;
    ConnectionPool pool1;
    List<ConnectionPool> pools;
    @Value("${db.pool.size}")
    Integer poolSize;

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
    private void postConstruct() {
        log.info("postConstruct method...");
    }
}

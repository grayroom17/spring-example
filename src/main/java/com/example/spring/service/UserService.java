package com.example.spring.service;

import com.example.spring.database.repository.CrudRepository;
import com.example.spring.database.repository.UserRepository;
import com.example.spring.database.utils.ConnectionPool;
import com.example.spring.entity.Department;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class UserService {

    CrudRepository<Integer, Department> departmentRepository;

    UserRepository userRepository;

    @Qualifier("pool1")
    ConnectionPool connectionPool;

    ConnectionPool pool1;

    @NonFinal
    ConnectionPool duplicatePool2;

    List<ConnectionPool> pools;

    @Value("${db.pool.size}")
    @NonFinal
    Integer poolSize;

    public ConnectionPool getDuplicatePool2() {
        return duplicatePool2;
    }

    @Autowired
    public void setDuplicatePool2(ConnectionPool pool1) {
        this.duplicatePool2 = pool1;
    }
}

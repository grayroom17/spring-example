package com.example.spring.database.repository;

import com.example.spring.bpp.InjectBean;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentRepository {
    @InjectBean
    UserRepository userRepository;
}

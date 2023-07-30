package com.example.spring.database.repository;

import com.example.spring.dto.PersonalInfo;
import com.example.spring.dto.UserFilter;
import com.example.spring.entity.Role;
import com.example.spring.entity.User;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter filter);

    List<User> findAllByQueryDslFilter(UserFilter filter);

    List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role);

}

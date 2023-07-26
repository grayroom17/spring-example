package com.example.spring.database.repository;

import com.example.spring.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Optional<Company> findByName(String name);

    List<Company> findByNameContainingIgnoreCase(String fragment);

}
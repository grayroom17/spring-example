package com.example.spring.database.repository;

import com.example.spring.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Optional<Company> findByName(String name);

    @Query("""
            select c
            from Company c
            join fetch c.locales cl
            where c.name = :name
            """)
    Optional<Company> findByNameWithLocales(String name);

    List<Company> findByNameContainingIgnoreCase(String fragment);

}
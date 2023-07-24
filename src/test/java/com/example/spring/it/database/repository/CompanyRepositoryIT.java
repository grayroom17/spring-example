package com.example.spring.it.database.repository;

import com.example.spring.entity.Company;
import com.example.spring.it.BaseIT;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Repeat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class CompanyRepositoryIT extends BaseIT {

    EntityManager entityManager;

    TransactionTemplate transactionTemplate;

    @Test
    @Transactional
    void findById() {
        Company company = entityManager.find(Company.class, 1);

        assertNotNull(company);
        Assertions.assertThat(company.getLocales()).hasSize(2);

    }

    @Test
    void findByIdWithTransactionTemplate() {

        transactionTemplate.executeWithoutResult(tx -> {
            Company company = entityManager.find(Company.class, 1);

            assertNotNull(company);
            Assertions.assertThat(company.getLocales()).hasSize(2);
        });

    }

    @Test
    @Transactional
    @Repeat(5)
    void save() {
        Company company = Company.builder()
                .name("Apple")
                .locales(Map.of(
                        "ru", "Apple описание",
                        "en", "Apple description"
                ))
                .build();
        entityManager.persist(company);

        assertNotNull(company.getId());
    }

}
package com.example.spring.database.repository;

import com.example.spring.entity.Role;
import com.example.spring.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            select u
            from User u
            where u.firstname like %:firstname%
            and u.lastname like %:lastname%
            """)
    List<User> findAllByFirstnameContainingAndLastnameContaining(String firstname, String lastname);


    @Query(nativeQuery = true,
            value = """
                    select *
                    from users u
                    where u.username = :username
                    """)
    List<User> findAllByUsername(String username);

    @Modifying(clearAutomatically = true)
    @Query("""
            update User u
            set u.role = :role
            where u.id in (:ids)
            """)
    int updateRole(Role role, Long... ids);

    Optional<User> findFirstByOrderByIdDesc();

    List<User> findFirst3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate birthDate);

    List<User> findFirst3ByBirthDateBefore(LocalDate birthDate, Sort sort);

    List<User> findAllBy(Pageable pageable);

    Slice<User> findUsersBy(Pageable pageable);

    Page<User> findBy(Pageable pageable);

    @EntityGraph("User.company")
    @Query(value = "select u from User u",
            countQuery = "select count (distinct u.firstname) from User u")
    Page<User> getAllBy(Pageable pageable);

    @EntityGraph(attributePaths = {"company", "company.locales"})
    @Query(value = "select u from User u",
            countQuery = "select count (distinct u.firstname) from User u")
    Page<User> getAllUsers(Pageable pageable);

}

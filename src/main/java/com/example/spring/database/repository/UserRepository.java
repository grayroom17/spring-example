package com.example.spring.database.repository;

import com.example.spring.dto.PersonalInfo;
import com.example.spring.dto.PersonalInfoInterface;
import com.example.spring.entity.Role;
import com.example.spring.entity.User;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE;


@Repository
public interface UserRepository extends
        JpaRepository<User, Long>,
        FilterUserRepository,
        RevisionRepository<User, Long, Integer> {

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

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<User> getFirst3ByBirthDateBefore(LocalDate birthDate, Sort sort);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<User> getTop3ByBirthDateBefore(LocalDate birthDate, Sort sort);

    @QueryHints(@QueryHint(name = HINT_FETCH_SIZE, value = "50"))
    List<User> getTop2ByBirthDateBefore(LocalDate birthDate, Sort sort);

    List<PersonalInfo> findAllByCompanyId(Integer companyId);

    <T> List<T> findAllByCompanyId(Integer companyId, Class<T> tClass);

    @Query(nativeQuery = true,
            value = """
                    select u.firstname,
                    u.lastname,
                    u.birth_date birthDate
                    from users u
                    where u.company_id = :companyId
                    """)
    List<PersonalInfoInterface> projectionWithInterface(Integer companyId);

}

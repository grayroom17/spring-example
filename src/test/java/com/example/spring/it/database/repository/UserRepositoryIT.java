package com.example.spring.it.database.repository;

import com.example.spring.database.querydsl.QPredicates;
import com.example.spring.database.repository.UserRepository;
import com.example.spring.dto.PersonalInfo;
import com.example.spring.dto.PersonalInfoInterface;
import com.example.spring.dto.UserFilter;
import com.example.spring.entity.Role;
import com.example.spring.entity.User;
import com.example.spring.it.BaseIT;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.history.Revision;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.example.spring.entity.QUser.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//@Transactional
class UserRepositoryIT extends BaseIT {

    UserRepository userRepository;

    @Test
    void checkQueries() {
        List<User> users = userRepository.findAllByFirstnameContainingAndLastnameContaining("a", "ov");
        assertThat(users).hasSize(3);
    }

    @Test
    void checkUpdate() {
        User user = userRepository.findById(1L).orElseThrow();
        assertSame(Role.ADMIN, user.getRole());

        int result = userRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(2, result);

        user = userRepository.findById(1L).orElseThrow();
        assertSame(Role.USER, user.getRole());
    }

    @Test
    void checkFindFirst() {
        Optional<User> firstUser = userRepository.findFirstByOrderByIdDesc();
        assertTrue(firstUser.isPresent());
        firstUser.ifPresent(user -> assertEquals(5L, user.getId()));
    }

    @Test
    void checkFirst3() {
        List<User> users = userRepository.findFirst3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate.now());
        assertThat(users).hasSize(3);
    }

    @Test
    void checkSort() {
//        Sort sortById = Sort.by("firstname").and(Sort.by("lastname"));
        Sort.TypedSort<User> sortBy = Sort.sort(User.class);
        Sort sort = sortBy.by(User::getFirstname)
                .and(sortBy.by(User::getLastname));
        List<User> users = userRepository.findFirst3ByBirthDateBefore(LocalDate.now(), sort.descending());
        assertThat(users).hasSize(3);
    }

    @Test
    void checkPageable() {
        PageRequest pageable = PageRequest.of(1, 2, Sort.by("id"));
        List<User> result = userRepository.findAllBy(pageable);
        assertThat(result).hasSize(2);
    }

    @Test
    void checkSlice() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("id"));
        Slice<User> slice = userRepository.findUsersBy(pageable);
        slice.forEach(user -> log.info(user.getId().toString()));
        log.info("____________");

        while (slice.hasNext()) {
            slice = userRepository.findUsersBy(slice.nextPageable());
            slice.forEach(user -> log.info(user.getId().toString()));
            log.info("____________");
        }
    }

    @Test
    void checkPage() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<User> page = userRepository.findBy(pageable);
        page.forEach(user -> log.info(user.getId().toString()));
        log.info("____________");

        while (page.hasNext()) {
            page = userRepository.findBy(page.nextPageable());
            page.forEach(user -> log.info(user.getId().toString()));
            log.info("____________");
        }

        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
    }

    @Test
    void checkNamedEntityGraph() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<User> page = userRepository.getAllBy(pageable);
        page.forEach(user -> log.info(user.getCompany().toString()));
    }

    @Test
    void checkEntityGraph() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<User> page = userRepository.getAllUsers(pageable);
        page.forEach(user -> log.info(user.getCompany().toString()));
    }

    @Test
    void checkPessimisticRead() {
        Sort.TypedSort<User> sortBy = Sort.sort(User.class);
        Sort sort = sortBy.by(User::getFirstname)
                .and(sortBy.by(User::getLastname));
        List<User> users = userRepository.getFirst3ByBirthDateBefore(LocalDate.now(), sort.descending());
        assertThat(users).hasSize(3);
    }

    @Test
    void checkPessimisticWrite() {
        Sort.TypedSort<User> sortBy = Sort.sort(User.class);
        Sort sort = sortBy.by(User::getFirstname)
                .and(sortBy.by(User::getLastname));
        List<User> users = userRepository.getTop3ByBirthDateBefore(LocalDate.now(), sort.descending());
        assertThat(users).hasSize(3);
    }

    @Test
    void checkProjections() {
        List<PersonalInfo> result = userRepository.findAllByCompanyId(1);
        assertThat(result).hasSize(2);
    }

    @Test
    void checkTypedProjections() {
        List<PersonalInfo> result = userRepository.findAllByCompanyId(1, PersonalInfo.class);
        assertThat(result).hasSize(2);
    }

    @Test
    void checkProjectionsWithInterface() {
        List<PersonalInfoInterface> result = userRepository.projectionWithInterface(1);
        assertThat(result).hasSize(2);
    }

    @Test
    void checkCustomImplementationUserRepository() {
        UserFilter filter = new UserFilter(null, "%ov%", LocalDate.now());
        List<User> result = userRepository.findAllByFilter(filter);
        assertThat(result).hasSize(4);
    }

    @Test
    void checkAuditing() {
        User user = userRepository.findById(1L).orElseThrow();
        user.setBirthDate(user.getBirthDate().plus(1, ChronoUnit.YEARS));
        userRepository.flush();
        assertThat(user.getModifiedAt()).isNotNull();
        assertThat(user.getModifiedBy()).isNotNull();
    }

//    @Commit
    @Test
    void checkHibernateEnvers() {
        User user = userRepository.findById(2L).orElseThrow();
        user.setBirthDate(user.getBirthDate().plus(1, ChronoUnit.YEARS));
        userRepository.flush();
        Optional<Revision<Integer, User>> lastChangeRevision = userRepository.findLastChangeRevision(2L);
        assertThat(lastChangeRevision).isNotNull();
    }

    @Test
    void checkQueryDsl() {
        UserFilter filter = new UserFilter(null, "ov", LocalDate.now());
        List<User> result = userRepository.findAllByQueryDslFilter(filter);
        assertThat(result).hasSize(4);
    }

    @Test
    void checkQueryDslExecutor() {
        UserFilter filter = new UserFilter(null, "ov", LocalDate.now());
        Predicate predicate = QPredicates.builder()
                .add(filter.firstname(), user.firstname::containsIgnoreCase)
                .add(filter.lastname(), user.lastname::containsIgnoreCase)
                .add(filter.birthDate(), user.birthDate::before)
                .build();
        List<User> result = (List<User>) userRepository.findAll(predicate);
        assertThat(result).hasSize(4);
    }

    @Test
    void checkJdbcTemplate() {
        List<PersonalInfo> users = userRepository.findAllByCompanyIdAndRole(1, Role.USER);
        assertThat(users).isNotNull().hasSize(1);
    }

    @Test
    void checkJdbcTemplateBatch() {
        List<User> allUsers = userRepository.findAll();
        assertDoesNotThrow(() -> userRepository.updateCompanyAndRole(allUsers));
    }

    @Test
    void checkNamedJdbcTemplateBatch() {
        List<User> allUsers = userRepository.findAll();
        assertDoesNotThrow(() -> userRepository.updateCompanyAndRoleNamed(allUsers));
    }

}
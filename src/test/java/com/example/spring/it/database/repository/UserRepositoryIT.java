package com.example.spring.it.database.repository;

import com.example.spring.database.repository.UserRepository;
import com.example.spring.dto.PersonalInfo;
import com.example.spring.dto.PersonalInfoInterface;
import com.example.spring.entity.Role;
import com.example.spring.entity.User;
import com.example.spring.it.BaseIT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class UserRepositoryIT extends BaseIT {

    UserRepository userRepository;

    @Test
    void checkQueries() {
        List<User> users = userRepository.findAllByFirstnameContainingAndLastnameContaining("a", "ov");
        assertThat(users).hasSize(3);
    }

    @Transactional
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

    @Transactional
    @Test
    void checkPageable() {
        PageRequest pageable = PageRequest.of(1, 2, Sort.by("id"));
        List<User> result = userRepository.findAllBy(pageable);
        assertThat(result).hasSize(2);
    }

    @Transactional
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

    @Transactional
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

    @Transactional
    @Test
    void checkNamedEntityGraph() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<User> page = userRepository.getAllBy(pageable);
        page.forEach(user -> log.info(user.getCompany().toString()));
    }

    @Transactional
    @Test
    void checkEntityGraph() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<User> page = userRepository.getAllUsers(pageable);
        page.forEach(user -> log.info(user.getCompany().toString()));
    }

    @Transactional
    @Test
    void checkPessimisticRead() {
        Sort.TypedSort<User> sortBy = Sort.sort(User.class);
        Sort sort = sortBy.by(User::getFirstname)
                .and(sortBy.by(User::getLastname));
        List<User> users = userRepository.getFirst3ByBirthDateBefore(LocalDate.now(), sort.descending());
        assertThat(users).hasSize(3);
    }

    @Transactional
    @Test
    void checkPessimisticWrite() {
        Sort.TypedSort<User> sortBy = Sort.sort(User.class);
        Sort sort = sortBy.by(User::getFirstname)
                .and(sortBy.by(User::getLastname));
        List<User> users = userRepository.getTop3ByBirthDateBefore(LocalDate.now(), sort.descending());
        assertThat(users).hasSize(3);
    }

    @Transactional
    @Test
    void checkProjections() {
        List<PersonalInfo> result = userRepository.findAllByCompanyId(1);
        assertThat(result).hasSize(2);
    }

    @Transactional
    @Test
    void checkTypedProjections() {
        List<PersonalInfo> result = userRepository.findAllByCompanyId(1, PersonalInfo.class);
        assertThat(result).hasSize(2);
    }

    @Transactional
    @Test
    void checkProjectionsWithInterface() {
        List<PersonalInfoInterface> result = userRepository.projectionWithInterface(1);
        assertThat(result).hasSize(2);
    }

}
package com.example.spring.it.database.repository;

import com.example.spring.database.repository.UserRepository;
import com.example.spring.entity.Role;
import com.example.spring.entity.User;
import com.example.spring.it.BaseIT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    void checkPageable(){
        PageRequest pageable = PageRequest.of(1, 2, Sort.by("id"));
        List<User> result = userRepository.findAllBy(pageable);
        assertThat(result).hasSize(2);
    }

}
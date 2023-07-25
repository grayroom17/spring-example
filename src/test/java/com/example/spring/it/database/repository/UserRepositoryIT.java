package com.example.spring.it.database.repository;

import com.example.spring.database.repository.UserRepository;
import com.example.spring.entity.User;
import com.example.spring.it.BaseIT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class UserRepositoryIT extends BaseIT {

    UserRepository userRepository;

    @Test
    void checkQueries() {
        List<User> users = userRepository.findAllByFirstnameContainingAndLastnameContaining("a", "ov");
        Assertions.assertThat(users).hasSize(3);
    }


}
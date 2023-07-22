package com.example.spring.it.service;

import com.example.spring.database.utils.ConnectionPool;
import com.example.spring.it.BaseIT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
class UserServiceIT extends BaseIT {

    ConnectionPool pool1;

    @Test
    void example() {
        assertTrue(true);
    }

}
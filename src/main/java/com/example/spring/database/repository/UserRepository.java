package com.example.spring.database.repository;

import com.example.spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


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

}

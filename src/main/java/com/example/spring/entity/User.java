package com.example.spring.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "users")
public class User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String username;

    LocalDate birthDate;

    String firstname;

    String lastname;

    @Enumerated(EnumType.STRING)
    Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    Company company;

    @Default
    @OneToMany(mappedBy = "user")
    Set<UserChat> userChats = new HashSet<>();

}

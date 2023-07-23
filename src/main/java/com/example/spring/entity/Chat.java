package com.example.spring.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.FieldDefaults;

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
public class Chat implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String name;

    @Default
    @OneToMany(mappedBy = "chat")
    Set<UserChat> userChats = new HashSet<>();

}

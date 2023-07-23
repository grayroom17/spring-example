package com.example.spring.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Company implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true, nullable = false)
    String name;

    @ElementCollection
    @CollectionTable(
            name = "company_locales",
            joinColumns = @JoinColumn(name = "company_id")
    )
    @MapKeyColumn(name = "lang")
    @Column(name = "description")
    @Default
    Map<String, String> locales = new HashMap<>();

}

package com.example.spring.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NamedEntityGraph(name = "User.company",
        attributeNodes = @NamedAttributeNode("company"))
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class User extends AuditingEntity<Long> {

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

    @NotAudited
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Default
    @OneToMany(mappedBy = "user")
    Set<UserChat> userChats = new HashSet<>();

}

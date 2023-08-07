package com.example.spring.database.repository;

import com.example.spring.database.querydsl.QPredicates;
import com.example.spring.dto.PersonalInfo;
import com.example.spring.dto.UserFilter;
import com.example.spring.entity.Role;
import com.example.spring.entity.User;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.spring.entity.QUser.user;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {

    EntityManager entityManager;

    JdbcTemplate jdbcTemplate;

    NamedParameterJdbcTemplate namedJdbcTemplate;

    private static final String FIND_BY_COMPANY_AND_ROLE = """
            select
                u.firstname,
                u.lastname,
                u.birth_date
            from users u
            where u.company_id = ?
            and u.role = ?
            """;

    private static final String UPDATE_COMPANY_AND_ROLE = """
            update users
            set company_id = ?,
                role = ?
            where id = ?
            """;

    private static final String UPDATE_COMPANY_AND_ROLE_NAMED = """
            update users
            set company_id = :companyId,
                role = :role
            where id = :id
            """;

    @Override
    public List<User> findAllByFilter(UserFilter filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);

        Root<User> user = criteria.from(User.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.firstname() != null && !filter.firstname().isEmpty() && !filter.firstname().isBlank()) {
            predicates.add(cb.like(user.get("firstname"), filter.firstname()));
        }
        if (filter.lastname() != null && !filter.lastname().isEmpty() && !filter.lastname().isBlank()) {
            predicates.add(cb.like(user.get("lastname"), filter.lastname()));
        }
        if (filter.birthDate() != null) {
            predicates.add(cb.lessThan(user.get("birthDate"), filter.birthDate()));
        }

        criteria.where(predicates.toArray(Predicate[]::new));
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<User> findAllByQueryDslFilter(UserFilter filter) {
        com.querydsl.core.types.Predicate predicate = QPredicates.builder()
                .add(filter.firstname(), user.firstname::containsIgnoreCase)
                .add(filter.lastname(), user.lastname::containsIgnoreCase)
                .add(filter.birthDate(), user.birthDate::before)
                .build();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }

    @Override
    public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
        return jdbcTemplate.query(FIND_BY_COMPANY_AND_ROLE,
                (rs, rowNum) -> new PersonalInfo(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getDate("birth_date").toLocalDate()
                ),
                companyId,
                role.name()
        );
    }

    @Override
    public void updateCompanyAndRole(List<User> users) {
        List<Object[]> args = users.stream()
                .map(user -> new Object[]{user.getCompany().getId(), user.getRole().name(), user.getId()})
                .toList();
        jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE, args);
    }

    @Override
    public void updateCompanyAndRoleNamed(List<User> users) {
        MapSqlParameterSource[] args = users.stream()
                .map(user -> Map.of(
                        "companyId", user.getCompany().getId(),
                        "role", user.getRole().name(),
                        "id", user.getId()
                ))
                .map(MapSqlParameterSource::new)
                .toArray(MapSqlParameterSource[]::new);
        namedJdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE_NAMED, args);
    }

}

package com.example.spring.mapper;

import com.example.spring.database.repository.CompanyRepository;
import com.example.spring.dto.UserCreateEditDto;
import com.example.spring.dto.UserReadDto;
import com.example.spring.entity.Company;
import com.example.spring.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = CompanyMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class UserMapper {

    @Autowired
    CompanyRepository companyRepository;

    @Mapping(target = "companyId", source = "user.company.id")
    public abstract UserReadDto toReadDto(User user);

    public abstract List<UserReadDto> toReadDtoList(List<User> users);

    public User fromUserCreateEditDto(UserCreateEditDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId()).orElseThrow();
        return fromUserCreateEditDto(dto, company);
    }


    @Mapping(target = "company", source = "company")
    @Mapping(target = "userChats", ignore = true)
    public abstract User fromUserCreateEditDto(UserCreateEditDto dto, Company company);

    public User updateFromCreateEditDto(User updatable, UserCreateEditDto updating) {
        Company company = companyRepository.findById(updating.getCompanyId()).orElseThrow();
        return updateFromCreateEditDto(updatable, updating, company);
    }

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userChats", ignore = true)
    public abstract User updateFromCreateEditDto(@MappingTarget User updatable, UserCreateEditDto updating, Company company);

}

package com.example.spring.mapper;

import com.example.spring.dto.CompanyReadDto;
import com.example.spring.entity.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyReadDto toReadDto(Company user);

    List<CompanyReadDto> toListReadDto(List<Company> users);

}

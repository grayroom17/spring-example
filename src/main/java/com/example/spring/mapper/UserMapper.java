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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static java.util.function.Predicate.not;

@Mapper(componentModel = "spring",
        uses = CompanyMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class UserMapper {

    @Autowired
    CompanyRepository companyRepository;

    public abstract UserReadDto toReadDto(User user);

    public abstract List<UserReadDto> toReadDtoList(List<User> users);

    public User fromUserCreateEditDto(UserCreateEditDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId()).orElseThrow();
        String imageFileName = getImageFileName(dto);
        return fromUserCreateEditDto(dto, company, imageFileName);
    }


    @Mapping(target = "company", source = "company")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "userChats", ignore = true)
    public abstract User fromUserCreateEditDto(UserCreateEditDto dto, Company company, String image);

    public User updateFromCreateEditDto(User updatable, UserCreateEditDto updating) {
        Company company = companyRepository.findById(updating.getCompanyId()).orElseThrow();
        String imageFileName = getImageFileName(updating);
        return updateFromCreateEditDto(updatable, updating, company, imageFileName);
    }

    @Mapping(target = "image", source = "image")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userChats", ignore = true)
    public abstract User updateFromCreateEditDto(@MappingTarget User updatable, UserCreateEditDto updating, Company company, String image);

    private static String getImageFileName(UserCreateEditDto updating) {
        MultipartFile image = Optional.ofNullable(updating.getImage())
                .filter(not(MultipartFile::isEmpty)).orElse(null);
        String imageFileName = null;
        if (image != null) {
            imageFileName = image.getOriginalFilename();
        }
        return imageFileName;
    }

}

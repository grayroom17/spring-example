package com.example.spring.service;

import com.example.spring.database.repository.CompanyRepository;
import com.example.spring.dto.CompanyReadDto;
import com.example.spring.listener.entity.AccessType;
import com.example.spring.listener.entity.EntityEvent;
import com.example.spring.mapper.CompanyMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CompanyService {

    CompanyRepository companyRepository;

    CompanyMapper companyMapper;
    ApplicationEventPublisher eventPublisher;

    public List<CompanyReadDto> findAll() {
        return companyMapper.toReadDtoList(companyRepository.findAll());
    }

    public Optional<CompanyReadDto> findById(Integer id) {
        return companyRepository.findById(id)
                .map(entity -> {
                    eventPublisher.publishEvent(new EntityEvent(entity, AccessType.READ));
                    return companyMapper.toReadDto(entity);
                });
    }

}

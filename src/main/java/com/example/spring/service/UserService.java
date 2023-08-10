package com.example.spring.service;

import com.example.spring.database.querydsl.QPredicates;
import com.example.spring.database.repository.UserRepository;
import com.example.spring.dto.UserCreateEditDto;
import com.example.spring.dto.UserFilter;
import com.example.spring.dto.UserReadDto;
import com.example.spring.mapper.UserMapper;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.example.spring.entity.QUser.user;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    ImageService imageService;

    public List<UserReadDto> findAll() {
        return userMapper.toReadDtoList(userRepository.findAll());
    }

    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
//        return userMapper.toReadDtoList(userRepository.findAllByQueryDslFilter(filter));
        Predicate predicate = QPredicates.builder()
                .add(filter.firstname(), user.firstname::containsIgnoreCase)
                .add(filter.lastname(), user.lastname::containsIgnoreCase)
                .add(filter.birthDate(), user.birthDate::before)
                .build();

        return userRepository.findAll(predicate, pageable)
                .map(userMapper::toReadDto);
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toReadDto);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return userMapper.fromUserCreateEditDto(dto);
                })
                .map(userRepository::save)
                .map(userMapper::toReadDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto dto) {
        return userRepository.findById(id)
                .map(user -> {
                    uploadImage(dto.getImage());
                    return userMapper.updateFromCreateEditDto(user, dto);
                })
                .map(userMapper::toReadDto);
    }

    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

}

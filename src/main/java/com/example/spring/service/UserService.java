package com.example.spring.service;

import com.example.spring.database.repository.UserRepository;
import com.example.spring.dto.UserCreateEditDto;
import com.example.spring.dto.UserFilter;
import com.example.spring.dto.UserReadDto;
import com.example.spring.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    public List<UserReadDto> findAll() {
        return userMapper.toReadDtoList(userRepository.findAll());
    }

    public List<UserReadDto> findAll(UserFilter filter) {
        return userMapper.toReadDtoList(userRepository.findAllByQueryDslFilter(filter));
    }

    public Optional<UserReadDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toReadDto);
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto dto) {
        return Optional.of(dto)
                .map(userMapper::fromUserCreateEditDto)
                .map(userRepository::save)
                .map(userMapper::toReadDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDto> update(Long id, UserCreateEditDto dto) {
        return userRepository.findById(id)
                .map(user -> userMapper.updateFromCreateEditDto(user, dto))
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


}

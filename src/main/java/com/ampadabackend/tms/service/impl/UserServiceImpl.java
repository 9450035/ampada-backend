package com.ampadabackend.tms.service.impl;

import com.ampadabackend.tms.controller.exception.SystemException;
import com.ampadabackend.tms.repository.UserRepository;
import com.ampadabackend.tms.service.UserService;
import com.ampadabackend.tms.service.dto.UserCreateDTO;
import com.ampadabackend.tms.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public void signUp(UserCreateDTO userCreateDTO) {

        if(userRepository.findByUsername(userCreateDTO.getUsername()).isPresent()){
            throw new SystemException(HttpStatus.BAD_REQUEST," user exist");
        }

        userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

        userRepository.save(userMapper.toEntity(userCreateDTO));
    }
}

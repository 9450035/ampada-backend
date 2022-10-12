package com.ampadabackend.tms.service;

import com.ampadabackend.tms.controller.exception.SystemException;
import com.ampadabackend.tms.domain.User;
import com.ampadabackend.tms.repository.UserRepository;
import com.ampadabackend.tms.service.dto.UserCreateDTO;
import com.ampadabackend.tms.service.impl.UserServiceImpl;
import com.ampadabackend.tms.service.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    private UserService userService;

    private final String ID = "id";

    private final String USERNAME = "username";

    private final String PASSWORD = "password";
    private final String ENCRYPT_PASSWORD = "p@$$w0rd";

    @BeforeEach
    void setup() {
        this.userService = new UserServiceImpl(userRepository, passwordEncoder, userMapper);
    }

    @Test
    void createUserSuccess() {
        var userCreateDTO = new UserCreateDTO(USERNAME, PASSWORD);
        var mappedUserCreateDTO = new UserCreateDTO(USERNAME, ENCRYPT_PASSWORD);
        var user = new User(USERNAME, ENCRYPT_PASSWORD);
        var savedUser = new User(ID, USERNAME, ENCRYPT_PASSWORD);

        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCRYPT_PASSWORD);
        Mockito.when(userRepository.save(user)).thenReturn(savedUser);
        Mockito.when(userMapper.toEntity(mappedUserCreateDTO)).thenReturn(user);
        userService.signUp(userCreateDTO);
    }

    @Test
    void createUserFail(){
        var userCreateDTO = new UserCreateDTO(USERNAME, PASSWORD);

        var foundUser = new User(ID, USERNAME, ENCRYPT_PASSWORD);

        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(foundUser));

        Assertions.assertThrows(SystemException.class, () ->
                userService.signUp(userCreateDTO));
    }

}

package com.ampadabackend.tms.controller;

import com.ampadabackend.tms.controller.exception.SystemException;
import com.ampadabackend.tms.service.UserService;
import com.ampadabackend.tms.service.dto.UserCreateDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    private UserService userService;

    private AuthController authController;

    private final String USERNAME = "username";
    private final String PASSWORD = "password";

    @BeforeEach
    void setup() {
        this.authController = new AuthController(userService);
    }

    @Test
    void signUpSuccess() {
        var userCreateDTO = new UserCreateDTO(USERNAME, PASSWORD);

        Mockito.doNothing().when(userService).signUp(userCreateDTO);

        Assertions.assertEquals(ResponseEntity.status(HttpStatus.CREATED).build(), authController.signUp(userCreateDTO));
    }

    @Test
    void signUpFail() {
        var userCreateDTO = new UserCreateDTO(USERNAME, PASSWORD);
        Mockito.doThrow(new SystemException(HttpStatus.BAD_REQUEST, "user exist")).when(userService).signUp(userCreateDTO);
        Assertions.assertThrows(SystemException.class, () ->
                authController.signUp(userCreateDTO));
    }
}

package com.ampadabackend.tms.controller;

import com.ampadabackend.tms.service.UserService;
import com.ampadabackend.tms.service.dto.TokenDTO;
import com.ampadabackend.tms.service.dto.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody UserCreateDTO userCreateDTO) {

        userService.signUp(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<TokenDTO> signIn(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(userService.sigIn(username, password));
    }
}

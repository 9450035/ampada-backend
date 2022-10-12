package com.ampadabackend.tms.controller;

import com.ampadabackend.tms.service.UserService;
import com.ampadabackend.tms.service.dto.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;


	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserCreateDTO userCreateDTO) {

		userService.signUp(userCreateDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
package com.ampadabackend.tms.service.impl;

import com.ampadabackend.tms.controller.exception.SystemException;
import com.ampadabackend.tms.domain.User;
import com.ampadabackend.tms.repository.UserRepository;
import com.ampadabackend.tms.security.jwt.JwtUtils;
import com.ampadabackend.tms.service.UserService;
import com.ampadabackend.tms.service.dto.TokenDTO;
import com.ampadabackend.tms.service.dto.UserCreateDTO;
import com.ampadabackend.tms.service.mapper.TokenDTOMapper;
import com.ampadabackend.tms.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final TokenDTOMapper tokenDTOMapper;

    @Override
    public void signUp(UserCreateDTO userCreateDTO) {

        if (userRepository.findByUsername(userCreateDTO.getUsername()).isPresent()) {
            throw new SystemException(HttpStatus.BAD_REQUEST, " user exist");
        }

        userCreateDTO.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

        userRepository.save(userMapper.toEntity(userCreateDTO));
    }

    @Override
    public TokenDTO sigIn(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenDTOMapper.toDTO(jwtUtils.generateJwtToken(authentication));
    }


}

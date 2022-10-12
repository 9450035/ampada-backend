package com.ampadabackend.tms.service.impl;

import com.ampadabackend.tms.domain.User;
import com.ampadabackend.tms.repository.UserRepository;
import com.ampadabackend.tms.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user Not Found with username: " + username));

        return user;

    }
}

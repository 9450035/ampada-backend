package com.ampadabackend.tms.service;

import com.ampadabackend.tms.service.dto.UserCreateDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void signUp(UserCreateDTO userCreateDTO);

}

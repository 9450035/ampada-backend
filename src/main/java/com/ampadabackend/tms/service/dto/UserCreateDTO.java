package com.ampadabackend.tms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

}

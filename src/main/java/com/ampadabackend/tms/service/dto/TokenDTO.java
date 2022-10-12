package com.ampadabackend.tms.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
}

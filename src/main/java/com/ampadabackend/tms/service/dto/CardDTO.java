package com.ampadabackend.tms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

    @NotEmpty
    private String cardTitle;

    private String member;

}

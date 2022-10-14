package com.ampadabackend.tms.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardViewModel {

    private String id;

    private String cardTitle;

    private Long creatOn;

    private Long modifiedOn;

    private BoardViewModel board;

    private UserViewModel member;

    public CardViewModel(String id) {
        this.id = id;
    }
}

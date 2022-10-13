package com.ampadabackend.tms.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardViewModel {

    private String id;

    private String boardName;

    private Long createOn;

    private Long modifiedOn;

    private UserViewModel creator;

    public BoardViewModel(String id) {
        this.id = id;
    }
}

package com.ampadabackend.tms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    private String id;

    private String boardName;

    private Long createOn;

    private Long modifiedOn;

    @DBRef
    private User creator;

    @DBRef
    private Set<Card> cards;

    public Board(String id) {
        this.id = id;
    }
}

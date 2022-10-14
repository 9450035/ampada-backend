package com.ampadabackend.tms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    private String id;

    private String cardTitle;

    private Long createOn;

    private Long modifiedOn;

    @DBRef
    private Board board;

    @DBRef
    private User member;

    public Card(String cardTitle, User member, Board board) {
        this.cardTitle = cardTitle;
        this.member = member;
        this.board = board;
    }
}

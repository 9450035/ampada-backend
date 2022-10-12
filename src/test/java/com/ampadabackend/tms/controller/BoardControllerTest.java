package com.ampadabackend.tms.controller;

import com.ampadabackend.tms.service.BoardService;
import com.ampadabackend.tms.service.dto.BoardCreateDTO;
import com.ampadabackend.tms.service.dto.BoardViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class BoardControllerTest {

    @Mock
    private BoardService boardService;

    private BoardController boardController;

    private final String BOARD_NAME = "doing";

    private final String ID = "ID";

    @BeforeEach
    void setup() {
        this.boardController = new BoardController(boardService);
    }

    @Test
    void create() {
        var boardCreateDTO = new BoardCreateDTO(BOARD_NAME);
        var boardViewModel = new BoardViewModel(ID);
        Mockito.when(boardService.create(boardCreateDTO)).thenReturn(boardViewModel);

        Assertions.assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(boardViewModel),
                boardController.create(boardCreateDTO));
    }

}

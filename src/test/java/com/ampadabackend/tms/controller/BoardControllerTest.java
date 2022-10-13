package com.ampadabackend.tms.controller;

import com.ampadabackend.tms.controller.exception.SystemException;
import com.ampadabackend.tms.service.BoardService;
import com.ampadabackend.tms.service.dto.BoardDTO;
import com.ampadabackend.tms.service.dto.BoardViewModel;
import com.ampadabackend.tms.service.dto.UserViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BoardControllerTest {

    @Mock
    private BoardService boardService;

    private BoardController boardController;

    private final String BOARD_NAME = "doing";

    private final Long CREATE_ON = System.currentTimeMillis();

    private final Long MODIFIED_ON = System.currentTimeMillis();

    private final String ID = "ID";

    private final String USERNAME = "username";

    private final String USER_ID = "userId";

    @BeforeEach
    void setup() {
        this.boardController = new BoardController(boardService);
    }

    @Test
    void create() {
        var boardCreateDTO = new BoardDTO(BOARD_NAME);
        var boardViewModel = new BoardViewModel(ID);
        Mockito.when(boardService.create(boardCreateDTO)).thenReturn(boardViewModel);

        Assertions.assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(boardViewModel),
                boardController.create(boardCreateDTO));
    }

    @Test
    void updateSuccess() {
        var boardCreateDTO = new BoardDTO(BOARD_NAME);
        var boardViewModel = new BoardViewModel(ID);
        Mockito.when(boardService.update(ID, boardCreateDTO)).thenReturn(boardViewModel);
        Assertions.assertEquals(ResponseEntity.ok().body(boardViewModel), boardController.update(ID, boardCreateDTO));
    }

    @Test
    void updateFail() {
        var boardCreateDTO = new BoardDTO(BOARD_NAME);
        Mockito.when(boardService.update(ID, boardCreateDTO)).thenThrow(new SystemException(HttpStatus.BAD_REQUEST, "board Not Exist"));
        Assertions.assertThrows(SystemException.class, () ->
                boardController.update(ID, boardCreateDTO));
    }

    @Test
    void findAll() {
        var userViewModel = new UserViewModel(USER_ID, USERNAME);
        var boardViewModel = new BoardViewModel(ID, BOARD_NAME, CREATE_ON, MODIFIED_ON, userViewModel);
        Mockito.when(boardService.findAll()).thenReturn(List.of(boardViewModel));

        Assertions.assertEquals(ResponseEntity.ok().body(List.of(boardViewModel)), boardController.findAll());
    }

}

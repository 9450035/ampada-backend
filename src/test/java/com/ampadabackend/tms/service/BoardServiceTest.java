package com.ampadabackend.tms.service;

import com.ampadabackend.tms.controller.exception.SystemException;
import com.ampadabackend.tms.domain.Board;
import com.ampadabackend.tms.domain.User;
import com.ampadabackend.tms.repository.BoardRepository;
import com.ampadabackend.tms.security.jwt.JwtUtils;
import com.ampadabackend.tms.service.dto.BoardDTO;
import com.ampadabackend.tms.service.dto.BoardViewModel;
import com.ampadabackend.tms.service.dto.UserViewModel;
import com.ampadabackend.tms.service.impl.BoardServiceImpl;
import com.ampadabackend.tms.service.mapper.BoardMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private BoardMapper boardMapper;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private CardService cardService;

    private BoardService boardService;

    private final String ID = "ID";

    private final String BOARD_NAME = "doing";

    private final String NEW_BOARD_NAME = "doing";

    private final Long CREATE_ON = System.currentTimeMillis();

    private final Long MODIFIED_ON = System.currentTimeMillis();

    private final String CREATOR_ID = "userId";


    private final String USERNAME = "username";

    private final String USER_ID = "userId";

    @BeforeEach
    void setup() {
        this.boardService = new BoardServiceImpl(boardRepository, jwtUtils, boardMapper, cardService);
    }

    @Test
    void create() {
        var boardCreateDTO = new BoardDTO(BOARD_NAME);
        var boardViewModel = new BoardViewModel(ID);
        var board = new Board(null, BOARD_NAME, CREATE_ON, null, new User(CREATOR_ID), null);
        var savedBoard = new Board(ID, BOARD_NAME, CREATE_ON, null, new User(CREATOR_ID), null);

        Mockito.when(boardMapper.toEntity(boardCreateDTO, CREATOR_ID)).thenReturn(board);
        Mockito.when(jwtUtils.getUserId()).thenReturn(CREATOR_ID);
        Mockito.when(boardRepository.save(board)).thenReturn(savedBoard);
        Mockito.when(boardMapper.toViewModel(ID)).thenReturn(boardViewModel);
        Assertions.assertEquals(boardViewModel, boardService.create(boardCreateDTO));

    }

    @Test
    void updateSuccess() {
        var boardCreateDTO = new BoardDTO(NEW_BOARD_NAME);
        var boardViewModel = new BoardViewModel(ID);
        var savedBoard = new Board(ID, BOARD_NAME, CREATE_ON, null, new User(CREATOR_ID), null);
        var updateBoard = new Board(ID, NEW_BOARD_NAME, CREATE_ON, MODIFIED_ON, new User(CREATOR_ID), null);

        Mockito.when(boardRepository.findById(ID)).thenReturn(Optional.of(savedBoard));
        Mockito.when(boardMapper.toEntity(boardCreateDTO, ID, CREATE_ON, CREATOR_ID)).thenReturn(updateBoard);
        Mockito.when(boardRepository.save(updateBoard)).thenReturn(updateBoard);
        Mockito.when(boardMapper.toViewModel(ID)).thenReturn(boardViewModel);

        Assertions.assertEquals(boardViewModel, boardService.update(ID, boardCreateDTO));
    }

    @Test
    void updateFail() {
        var boardCreateDTO = new BoardDTO(NEW_BOARD_NAME);

        Mockito.when(boardRepository.findById(ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(SystemException.class, () ->
                boardService.update(ID, boardCreateDTO));
    }

    @Test
    void findAll() {
        var board = new Board(ID, NEW_BOARD_NAME, CREATE_ON, MODIFIED_ON, new User(CREATOR_ID), null);
        var userViewModel = new UserViewModel(USER_ID, USERNAME);
        var boardViewModel = new BoardViewModel(ID, BOARD_NAME, CREATE_ON, MODIFIED_ON, userViewModel);
        Mockito.when(boardRepository.findAll()).thenReturn(List.of(board));
        Mockito.when(boardMapper.toViewModel(board)).thenReturn(boardViewModel);

        Assertions.assertEquals(List.of(boardViewModel), boardService.findAll());
    }

    @Test
    void deleteSuccess() {
        Mockito.when(cardService.exist(ID)).thenReturn(false);
        Mockito.doNothing().when(boardRepository).deleteById(ID);
        boardService.delete(ID);
    }

    @Test
    void deleteFail() {
        Mockito.when(cardService.exist(ID)).thenReturn(true);
        Assertions.assertThrows(SystemException.class, () ->
                boardService.delete(ID));
    }

}

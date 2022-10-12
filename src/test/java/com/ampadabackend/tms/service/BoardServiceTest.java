package com.ampadabackend.tms.service;

import com.ampadabackend.tms.domain.Board;
import com.ampadabackend.tms.domain.User;
import com.ampadabackend.tms.repository.BoardRepository;
import com.ampadabackend.tms.security.jwt.JwtUtils;
import com.ampadabackend.tms.service.dto.BoardCreateDTO;
import com.ampadabackend.tms.service.dto.BoardViewModel;
import com.ampadabackend.tms.service.impl.BoardServiceImpl;
import com.ampadabackend.tms.service.mapper.BoardMapper;
import io.jsonwebtoken.JwtBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private BoardMapper boardMapper;

    @Mock
    private JwtUtils jwtUtils;

    private BoardService boardService;

    private final String ID = "ID";
    private final String BOARD_NAME = "doing";
    private final Long CREATE_ON = System.currentTimeMillis();
    private final String CREATOR_ID = "userId";

    @BeforeEach
    void setup() {
        this.boardService = new BoardServiceImpl(boardRepository, jwtUtils, boardMapper);
    }

    @Test
    void create() {
        var boardCreateDTO = new BoardCreateDTO(BOARD_NAME);
        var boardViewModel = new BoardViewModel(ID);
        var board = new Board(null, BOARD_NAME, CREATE_ON, null, new User(CREATOR_ID));
        var savedBoard = new Board(ID, BOARD_NAME, CREATE_ON, null, new User(CREATOR_ID));

        Mockito.when(boardMapper.toEntity(boardCreateDTO, CREATOR_ID)).thenReturn(board);
        Mockito.when(jwtUtils.getUserId()).thenReturn(CREATOR_ID);
        Mockito.when(boardRepository.save(board)).thenReturn(savedBoard);
        Mockito.when(boardMapper.toViewModel(ID)).thenReturn(boardViewModel);
        Assertions.assertEquals(boardViewModel, boardService.create(boardCreateDTO));

    }

}

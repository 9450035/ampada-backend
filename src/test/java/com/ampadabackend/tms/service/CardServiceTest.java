package com.ampadabackend.tms.service;

import com.ampadabackend.tms.controller.exception.SystemException;
import com.ampadabackend.tms.domain.Board;
import com.ampadabackend.tms.domain.Card;
import com.ampadabackend.tms.domain.User;
import com.ampadabackend.tms.repository.CardRepository;
import com.ampadabackend.tms.service.dto.CardDTO;
import com.ampadabackend.tms.service.dto.CardViewModel;
import com.ampadabackend.tms.service.impl.CardServiceImpl;
import com.ampadabackend.tms.service.mapper.CardMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardMapper cardMember;

    @Mock
    private BoardService boardService;

    @Mock
    private UserService userService;

    private CardService cardService;

    private final String CARD_TITLE = "title";

    private final String CARD_ID = "card_id";

    private final String BOARD_ID = "board_id";

    private final Long CREATE_ON = System.currentTimeMillis();

    private final Long MODIFIED_ON = System.currentTimeMillis();

    private final String USER_ID = "user_id";

    private final String BOARD_NAME = "board_name";

    private final String USERNAME = "username";

    @BeforeEach
    void setup() {
        cardService = new CardServiceImpl(cardRepository, cardMember, boardService, userService);
    }

    @Test
    void create() {
        var cardDTO = new CardDTO(CARD_TITLE, USER_ID);
        var cardViewModel = new CardViewModel(CARD_ID);
        var card = new Card(null, CARD_TITLE, CREATE_ON, null, new Board(BOARD_ID), new User(USER_ID));
        var savedCard = new Card(CARD_ID, CARD_TITLE, CREATE_ON, null, new Board(BOARD_ID), new User(USER_ID));
        Mockito.when(userService.exist(USER_ID)).thenReturn(true);
        Mockito.when(boardService.exist(BOARD_ID)).thenReturn(true);
        Mockito.when(cardMember.toEntity(cardDTO, BOARD_ID)).thenReturn(card);
        Mockito.when(cardMember.toViewModel(CARD_ID)).thenReturn(cardViewModel);
        Mockito.when(cardRepository.save(card)).thenReturn(savedCard);

        Assertions.assertEquals(cardViewModel, cardService.create(BOARD_ID, cardDTO));

    }

    @Test
    void updateSuccess() {
        var cardDTO = new CardDTO(CARD_TITLE, USER_ID);
        var cardViewModel = new CardViewModel(CARD_ID);
        var savedCard = new Card(CARD_ID, CARD_TITLE, CREATE_ON, null, new Board(BOARD_ID), new User(USER_ID));
        var update = new Card(CARD_ID, CARD_TITLE, CREATE_ON, MODIFIED_ON, new Board(BOARD_ID), new User(USER_ID));
        Mockito.when(cardRepository.findById(CARD_ID)).thenReturn(Optional.of(savedCard));
        Mockito.when(userService.exist(USER_ID)).thenReturn(true);
        Mockito.when(boardService.exist(BOARD_ID)).thenReturn(true);
        Mockito.when(cardMember.toEntity(cardDTO, CARD_ID, BOARD_ID, CREATE_ON)).thenReturn(update);
        Mockito.when(cardRepository.save(update)).thenReturn(update);
        Mockito.when(cardMember.toViewModel(CARD_ID)).thenReturn(cardViewModel);

        Assertions.assertEquals(cardViewModel, cardService.update(cardDTO, BOARD_ID, CARD_ID));
    }

    @Test
    void updateFail() {
        var cardDTO = new CardDTO(CARD_TITLE, USER_ID);
        Mockito.when(cardRepository.findById(CARD_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(SystemException.class, () ->
                cardService.update(cardDTO, BOARD_ID, CARD_ID));
    }

    @Test
    void updateFailBoardNotFound() {
        var cardDTO = new CardDTO(CARD_TITLE, USER_ID);
        var savedCard = new Card(CARD_ID, CARD_TITLE, CREATE_ON, null, new Board(BOARD_ID), new User(USER_ID));
        Mockito.when(cardRepository.findById(CARD_ID)).thenReturn(Optional.of(savedCard));
        Mockito.when(boardService.exist(BOARD_ID)).thenReturn(false);
        Assertions.assertThrows(SystemException.class, () ->
                cardService.update(cardDTO, BOARD_ID, CARD_ID));
    }

    @Test
    void updateFailUserNotFound() {
        var cardDTO = new CardDTO(CARD_TITLE, USER_ID);
        var savedCard = new Card(CARD_ID, CARD_TITLE, CREATE_ON, null, new Board(BOARD_ID), new User(USER_ID));
        Mockito.when(cardRepository.findById(CARD_ID)).thenReturn(Optional.of(savedCard));
        Mockito.when(userService.exist(USER_ID)).thenReturn(false);
        Mockito.when(boardService.exist(BOARD_ID)).thenReturn(true);
        Assertions.assertThrows(SystemException.class, () ->
                cardService.update(cardDTO, BOARD_ID, CARD_ID));
    }

    @Test
    void filter() {
        Mockito.when(cardRepository.findAll(Example.of(new Card(CARD_TITLE, USER_ID != null ? new User(USER_ID) : null, new Board(BOARD_ID)),
                ExampleMatcher.matchingAll().withIgnoreNullValues().withMatcher("cardTitle",
                        new ExampleMatcher.GenericPropertyMatcher().contains())), Pageable.unpaged())).thenReturn(Page.empty());
        Assertions.assertEquals(Page.empty(), cardService.filter(BOARD_ID, CARD_TITLE, USER_ID, Pageable.unpaged()));
    }

    @Test
    void delete() {
        Mockito.doNothing().when(cardRepository).deleteById(CARD_ID);
        cardService.delete(CARD_ID);
    }

}

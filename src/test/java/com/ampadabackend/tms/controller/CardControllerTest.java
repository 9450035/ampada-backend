package com.ampadabackend.tms.controller;

import com.ampadabackend.tms.controller.exception.SystemException;
import com.ampadabackend.tms.service.CardService;
import com.ampadabackend.tms.service.dto.BoardViewModel;
import com.ampadabackend.tms.service.dto.CardDTO;
import com.ampadabackend.tms.service.dto.CardViewModel;
import com.ampadabackend.tms.service.dto.UserViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CardControllerTest {

    @Mock
    private CardService cardService;

    private CardController cardController;

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
        cardController = new CardController(cardService);
    }

    @Test
    void create() {
        var cardDTO = new CardDTO(CARD_TITLE, USER_ID);
        var cardViewModel = new CardViewModel(CARD_ID);

        Mockito.when(cardService.create(BOARD_ID, cardDTO)).thenReturn(cardViewModel);

        Assertions.assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(cardViewModel), cardController.create(BOARD_ID, cardDTO));
    }

    @Test
    void updateSuccess() {
        var cardDTO = new CardDTO(CARD_TITLE, USER_ID);
        var cardViewModel = new CardViewModel(CARD_ID);

        Mockito.when(cardService.update(cardDTO, BOARD_ID, CARD_ID)).thenReturn(cardViewModel);

        Assertions.assertEquals(ResponseEntity.ok().body(cardViewModel), cardController.update(BOARD_ID, CARD_ID, cardDTO));
    }

    @Test
    void updateFail() {
        var cardDTO = new CardDTO(CARD_TITLE, USER_ID);
        var cardViewModel = new CardViewModel(CARD_ID);

        Mockito.when(cardService.update(cardDTO, BOARD_ID, CARD_ID)).thenThrow(new SystemException(HttpStatus.BAD_REQUEST, "card not found"));

        Assertions.assertThrows(SystemException.class, () ->
                cardController.update(BOARD_ID, CARD_ID, cardDTO));
    }

    @Test
    void filter() {
        var user = new UserViewModel(USER_ID, USERNAME);
        var board = new BoardViewModel(BOARD_ID, BOARD_NAME, CREATE_ON, MODIFIED_ON, user);
        var carViewModel = new CardViewModel(CARD_ID, CARD_TITLE, CREATE_ON, MODIFIED_ON, board, user);
        var expected = new PageImpl<>(List.of(carViewModel));
        Mockito.when(cardService.filter(BOARD_ID, CARD_TITLE, null, Pageable.unpaged())).thenReturn(expected);
        Assertions.assertEquals(ResponseEntity.ok(expected), cardController.find(BOARD_ID, CARD_TITLE, null, Pageable.unpaged()));
    }

    @Test
    void delete() {
        Mockito.doNothing().when(cardService).delete(CARD_ID);
        Assertions.assertEquals(ResponseEntity.noContent().build(), cardController.delete(BOARD_ID, CARD_ID));
    }


}

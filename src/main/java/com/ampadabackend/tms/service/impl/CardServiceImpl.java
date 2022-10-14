package com.ampadabackend.tms.service.impl;

import com.ampadabackend.tms.controller.exception.SystemException;
import com.ampadabackend.tms.domain.Board;
import com.ampadabackend.tms.domain.Card;
import com.ampadabackend.tms.domain.User;
import com.ampadabackend.tms.repository.CardRepository;
import com.ampadabackend.tms.service.BoardService;
import com.ampadabackend.tms.service.CardService;
import com.ampadabackend.tms.service.UserService;
import com.ampadabackend.tms.service.dto.CardDTO;
import com.ampadabackend.tms.service.dto.CardViewModel;
import com.ampadabackend.tms.service.mapper.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMember;
    private final BoardService boardService;
    private final UserService userService;

    @Override
    public CardViewModel create(String boardId, CardDTO cardDTO) {

        validate(boardId, cardDTO);
        return cardMember.toViewModel(cardRepository.save(cardMember.toEntity(cardDTO, boardId)).getId());
    }

    @Override
    public CardViewModel update(CardDTO cardDTO, String boardId, String cardId) {
        return cardRepository.findById(cardId).map(card -> {
            validate(boardId, cardDTO);
            return cardMember.toViewModel(cardRepository.save(cardMember.toEntity(cardDTO, cardId, boardId, card.getCreatOn())).getId());
        }).orElseThrow(() -> new SystemException(HttpStatus.BAD_REQUEST, "card not found"));

    }

    public void validate(String boardId, CardDTO cardDTO) {

        if (!boardService.exist(boardId)) {
            throw new SystemException(HttpStatus.BAD_REQUEST, "board not exist");
        }
        if (cardDTO.getMember() != null && !userService.exist(cardDTO.getMember())) {
            throw new SystemException(HttpStatus.BAD_REQUEST, "user not exist");
        }
    }

    @Override
    public void delete(String cardId) {
        cardRepository.deleteById(cardId);
    }

    @Override
    public Page<CardViewModel> filter(String boardId, String title, String userId, Pageable pageable) {
        return cardRepository.findAll(Example.of(new Card(title, userId != null ? new User(userId) : null, new Board(boardId)),
                        ExampleMatcher.matchingAll().withIgnoreNullValues().withMatcher("cardTitle",
                                new ExampleMatcher.GenericPropertyMatcher().contains())), pageable)
                .map(cardMember::toViewModel);
    }

    @Override
    public boolean exist(String boardId) {
        return cardRepository.existsByBoardId(boardId);
    }
}

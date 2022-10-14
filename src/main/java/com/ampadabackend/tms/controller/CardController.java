package com.ampadabackend.tms.controller;

import com.ampadabackend.tms.service.CardService;
import com.ampadabackend.tms.service.dto.CardDTO;
import com.ampadabackend.tms.service.dto.CardViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/board/{boardId}/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardViewModel> create(@PathVariable String boardId, @RequestBody @Valid CardDTO cardDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.create(boardId, cardDTO));
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CardViewModel> update(@PathVariable String boardId, @PathVariable String cardId,
                                                @RequestBody @Valid CardDTO cardDTO) {
        return ResponseEntity.ok(cardService.update(cardDTO, boardId, cardId));
    }

    @GetMapping
    public ResponseEntity<Page<CardViewModel>> find(@PathVariable String boardId, @RequestParam(required = false) String title,
                                                    @RequestParam(required = false) String userId, Pageable pageable) {
        return ResponseEntity.ok(cardService.filter(boardId, title, userId, pageable));
    }

    @DeleteMapping("/{cardId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable String boardId, @PathVariable String cardId) {
        cardService.delete(cardId);
        return ResponseEntity.noContent().build();
    }

}

package com.ampadabackend.tms.service;

import com.ampadabackend.tms.service.dto.CardDTO;
import com.ampadabackend.tms.service.dto.CardViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CardService {

    CardViewModel create(String boardId, CardDTO cardDTO);

    CardViewModel update(CardDTO cardDTO, String boardId, String cardId);

    void validate(String boardId, CardDTO cardDTO);

    void delete(String cardId);

    Page<CardViewModel> filter(String boardId, String title, String userId, Pageable pageable);
}

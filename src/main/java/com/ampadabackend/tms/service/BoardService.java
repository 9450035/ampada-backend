package com.ampadabackend.tms.service;

import com.ampadabackend.tms.service.dto.BoardCreateDTO;
import com.ampadabackend.tms.service.dto.BoardViewModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    BoardViewModel create(BoardCreateDTO boardCreateDTO);

    BoardViewModel update(String id, BoardCreateDTO boardCreateDTO);

    List<BoardViewModel> findAll();
}

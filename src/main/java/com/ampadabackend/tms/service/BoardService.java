package com.ampadabackend.tms.service;

import com.ampadabackend.tms.service.dto.BoardDTO;
import com.ampadabackend.tms.service.dto.BoardViewModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    BoardViewModel create(BoardDTO boardCreateDTO);

    BoardViewModel update(String id, BoardDTO boardCreateDTO);

    List<BoardViewModel> findAll();

    boolean exist(String boardId);

    void delete(String boardId);
}

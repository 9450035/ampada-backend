package com.ampadabackend.tms.service.impl;

import com.ampadabackend.tms.controller.exception.SystemException;
import com.ampadabackend.tms.repository.BoardRepository;
import com.ampadabackend.tms.security.jwt.JwtUtils;
import com.ampadabackend.tms.service.BoardService;
import com.ampadabackend.tms.service.dto.BoardDTO;
import com.ampadabackend.tms.service.dto.BoardViewModel;
import com.ampadabackend.tms.service.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final JwtUtils jwtUtils;
    private final BoardMapper boardMapper;

    @Override
    public BoardViewModel create(BoardDTO boardCreateDTO) {

        return boardMapper.toViewModel(boardRepository.save(boardMapper.toEntity(boardCreateDTO, jwtUtils.getUserId())).getId());
    }

    @Override
    public BoardViewModel update(String id, BoardDTO boardCreateDTO) {
        var board = boardRepository.findById(id);
        if (board.isEmpty())
            throw new SystemException(HttpStatus.BAD_REQUEST, "board Not Exist");

        return boardMapper.toViewModel(boardRepository.save(boardMapper.toEntity(boardCreateDTO, id,
                board.get().getCreateOn(), board.get().getCreator().getId())).getId());
    }

    @Override
    public List<BoardViewModel> findAll() {
        return boardRepository.findAll().stream()
                .map(boardMapper::toViewModel).collect(Collectors.toList());
    }

    @Override
    public boolean exist(String boardId) {
        return boardRepository.existsById(boardId);
    }
}

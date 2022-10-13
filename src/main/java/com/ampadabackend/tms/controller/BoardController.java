package com.ampadabackend.tms.controller;

import com.ampadabackend.tms.service.BoardService;
import com.ampadabackend.tms.service.dto.BoardCreateDTO;
import com.ampadabackend.tms.service.dto.BoardViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardViewModel> create(@RequestBody BoardCreateDTO boardCreateDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.create(boardCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardViewModel> update(@PathVariable String id, @RequestBody BoardCreateDTO boardCreateDTO) {

        return ResponseEntity.ok().body(boardService.update(id, boardCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<BoardViewModel>> findAll() {
        return ResponseEntity.ok(boardService.findAll());
    }
}

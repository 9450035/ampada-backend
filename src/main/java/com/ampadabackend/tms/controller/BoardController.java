package com.ampadabackend.tms.controller;

import com.ampadabackend.tms.service.BoardService;
import com.ampadabackend.tms.service.dto.BoardDTO;
import com.ampadabackend.tms.service.dto.BoardViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Validated
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardViewModel> create(@RequestBody @Valid BoardDTO boardCreateDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.create(boardCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardViewModel> update(@PathVariable String id, @RequestBody BoardDTO boardCreateDTO) {

        return ResponseEntity.ok().body(boardService.update(id, boardCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<BoardViewModel>> findAll() {
        return ResponseEntity.ok(boardService.findAll());
    }

    @DeleteMapping("/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable String boardId) {
        boardService.delete(boardId);
        return ResponseEntity.noContent().build();
    }
}

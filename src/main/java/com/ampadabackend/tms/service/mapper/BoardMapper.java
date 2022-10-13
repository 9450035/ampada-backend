package com.ampadabackend.tms.service.mapper;

import com.ampadabackend.tms.domain.Board;
import com.ampadabackend.tms.service.dto.BoardDTO;
import com.ampadabackend.tms.service.dto.BoardViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {UserMapper.class})
public interface BoardMapper {

    @Mapping(target = "createOn",expression = "java(System.currentTimeMillis())")
    Board toEntity(BoardDTO boardCreateDTO, String creator);

    @Mapping(target = "modifiedOn", expression = "java(System.currentTimeMillis())")
    Board toEntity(BoardDTO boardCreateDTO, String id, Long createOn, String creatorId);

    BoardViewModel toViewModel(String id);

    BoardViewModel toViewModel(Board board);
}

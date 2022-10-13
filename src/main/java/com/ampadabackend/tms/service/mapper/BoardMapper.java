package com.ampadabackend.tms.service.mapper;

import com.ampadabackend.tms.domain.Board;
import com.ampadabackend.tms.service.dto.BoardCreateDTO;
import com.ampadabackend.tms.service.dto.BoardViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {UserMapper.class})
public interface BoardMapper {

    @Mapping(target = "createOn",expression = "java(System.currentTimeMillis())")
    Board toEntity(BoardCreateDTO boardCreateDTO,String creatorId);

    @Mapping(target = "modifiedOn", expression = "java(System.currentTimeMillis())")
    Board toEntity(BoardCreateDTO boardCreateDTO,String id,Long createOn, String creatorId);

    BoardViewModel toViewModel(String id);

    BoardViewModel toViewModel(Board board);
}

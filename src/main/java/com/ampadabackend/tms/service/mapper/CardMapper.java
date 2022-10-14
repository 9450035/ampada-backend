package com.ampadabackend.tms.service.mapper;

import com.ampadabackend.tms.domain.Card;
import com.ampadabackend.tms.service.dto.CardDTO;
import com.ampadabackend.tms.service.dto.CardViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BoardMapper.class})
public interface CardMapper {

    @Mapping(target = "creatOn", expression = "java(System.currentTimeMillis())")
    Card toEntity(CardDTO cardDTO, String board);

    @Mapping(target = "modifiedOn", expression = "java(System.currentTimeMillis())")
    Card toEntity(CardDTO cardDTO,String id, String board,Long createOn);

    CardViewModel toViewModel(String id);

    CardViewModel toViewModel(Card card);

}

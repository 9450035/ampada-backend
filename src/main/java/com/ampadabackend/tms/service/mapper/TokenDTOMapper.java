package com.ampadabackend.tms.service.mapper;

import com.ampadabackend.tms.service.dto.TokenDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenDTOMapper {

    TokenDTO toDTO(String token);

}

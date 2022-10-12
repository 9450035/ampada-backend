package com.ampadabackend.tms.service.mapper;


import com.ampadabackend.tms.domain.User;
import com.ampadabackend.tms.service.dto.UserCreateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserCreateDTO userCreateDTO);

    default User fromId(String id) {
        if(id==null){
            return null;
        }
        return new User(id);
    }

}

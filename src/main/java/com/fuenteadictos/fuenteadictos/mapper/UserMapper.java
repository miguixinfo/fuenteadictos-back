package com.fuenteadictos.fuenteadictos.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.fuenteadictos.fuenteadictos.domain.User;
import com.fuenteadictos.fuenteadictos.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDto(User entity);

    User toEntity(UserDTO dto);

}

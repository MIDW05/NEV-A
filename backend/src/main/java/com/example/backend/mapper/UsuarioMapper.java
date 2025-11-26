package com.example.backend.mapper;

import com.example.backend.dto.UsuarioDTO;
import com.example.backend.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

        UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
        UsuarioDTO usuarioEntityAUsDTO(UsuarioEntity usuarioEntity);
        UsuarioEntity usuarioDTOAUsEntity(UsuarioDTO usuarioDTO);
}

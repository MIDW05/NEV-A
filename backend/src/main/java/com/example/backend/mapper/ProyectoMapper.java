package com.example.backend.mapper;

import com.example.backend.dto.ProyectoDTO;
import com.example.backend.entity.ProyectoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProyectoMapper {
    ProyectoMapper INSTANCE = Mappers.getMapper(ProyectoMapper.class);

    ProyectoDTO proyectoEntityAProyectoDTO(ProyectoEntity proyectoEntity);
    ProyectoEntity proyectoDTOAProyectoEntity(ProyectoDTO proyectoDTO);
}

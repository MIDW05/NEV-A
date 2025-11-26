package com.example.backend.mapper;

import com.example.backend.dto.NotaDTO;
import com.example.backend.entity.NotaEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotaMapper {

    NotaMapper INSTANCE = Mappers.getMapper(NotaMapper.class);

    @Mappings({
            @Mapping(source = "usuario.id", target = "usuarioId"),
            @Mapping(source = "tarea.id",   target = "tareaId")
    })
    NotaDTO notaEntityANotaDTO(NotaEntity notaEntity);

    @InheritInverseConfiguration
    @Mappings({
            // usuario y tarea se resuelven en el servicio
            @Mapping(target = "usuario", ignore = true),
            @Mapping(target = "tarea",   ignore = true)
    })
    NotaEntity notaDTOANotaEntity(NotaDTO notaDTO);
}

package com.example.backend.mapper;

import com.example.backend.dto.NotaDTO;
import com.example.backend.entity.NotaEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotaMapper {

    NotaMapper INSTANCE = Mappers.getMapper(NotaMapper.class);

    @Mappings({
            @Mapping(source = "usuario.id", target = "usuarioId"),
            @Mapping(source = "tarea.id", target = "tareaId")
    })
    NotaDTO notaEntityANotaDTO(NotaEntity notaEntity);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "usuario", ignore = true),
            @Mapping(target = "tarea", ignore = true)
    })
    NotaEntity notaDTOANotaEntity(NotaDTO notaDTO);
}

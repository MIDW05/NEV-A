package com.example.backend.mapper;

import com.example.backend.dto.PomodoroDTO;
import com.example.backend.entity.PomodoroSesionEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PomodoroMapper {

    @Mappings({
            @Mapping(source = "usuario.id", target = "usuarioId"),
            @Mapping(source = "tarea.id", target = "tareaId")
    })
    PomodoroDTO entityToDto(PomodoroSesionEntity e);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "usuario", ignore = true),
            @Mapping(target = "tarea", ignore = true)
    })
    PomodoroSesionEntity dtoToEntity(PomodoroDTO dto);
}

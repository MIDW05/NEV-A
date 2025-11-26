package com.example.backend.mapper;

import com.example.backend.dto.PomodoroDTO;
import com.example.backend.entity.PomodoroSesionEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PomodoroMapper {

    PomodoroMapper INSTANCE = Mappers.getMapper(PomodoroMapper.class);

    @Mappings({
            @Mapping(source = "usuario.id", target = "usuarioId"),
            @Mapping(source = "tarea.id", target = "tareaId"),
            @Mapping(source = "proyecto.id", target = "proyectoId")
    })
    PomodoroDTO pomodoroEntityAPomodoroDTO(PomodoroSesionEntity pomodoroSesionEntity);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "usuario", ignore = true),
            @Mapping(target = "tarea", ignore = true),
            @Mapping(target = "proyecto", ignore = true)
    })
    PomodoroSesionEntity pomodoroDTOAPomodoroEntity(PomodoroDTO pomodoroDTO);
}

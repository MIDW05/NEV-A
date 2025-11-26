package com.example.backend.mapper;

import com.example.backend.dto.ProyectoDTO;
import com.example.backend.entity.ProyectoEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProyectoMapper {

    ProyectoMapper INSTANCE = Mappers.getMapper(ProyectoMapper.class);

    @Mappings({
            @Mapping(source = "usuario.id", target = "usuarioId")
    })
    ProyectoDTO proyectoEntityAProyectoDTO(ProyectoEntity proyectoEntity);

    @InheritInverseConfiguration
    @Mappings({
            // usuario se resuelve en el servicio con UsuarioRepository
            @Mapping(target = "usuario", ignore = true)
    })
    ProyectoEntity proyectoDTOAProyectoEntity(ProyectoDTO proyectoDTO);
}

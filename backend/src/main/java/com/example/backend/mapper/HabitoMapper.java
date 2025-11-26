package com.example.backend.mapper;

import com.example.backend.dto.HabitoDTO;
import com.example.backend.entity.HabitoEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HabitoMapper {

    HabitoMapper INSTANCE = Mappers.getMapper(HabitoMapper.class);

    @Mappings({
            @Mapping(source = "usuario.id", target = "usuarioId")
    })
    HabitoDTO habitoEntityAHabitoDTO(HabitoEntity habitoEntity);

    @InheritInverseConfiguration
    @Mapping(target = "usuario", ignore = true)
    HabitoEntity habitoDTOAHabitoEntity(HabitoDTO habitoDTO);
}

package com.example.backend.mapper;

import com.example.backend.dto.HabitoDTO;
import com.example.backend.entity.HabitoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HabitoMapper {
    HabitoMapper INSTANCE = Mappers.getMapper(HabitoMapper.class);

    HabitoDTO habitoEntityAHabitoDTO(HabitoEntity habitoEntity);
    HabitoEntity habitoDTOAHabitoEntity(HabitoDTO habitoDTO);
}

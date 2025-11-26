package com.example.backend.mapper;

import com.example.backend.dto.NotaDTO;
import com.example.backend.entity.NotaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotaMapper {
    NotaMapper INSTANCE = Mappers.getMapper(NotaMapper.class);

    NotaDTO notaEntityANotaDTO(NotaEntity notaEntity);
    NotaEntity notaDTOANotaEntity(NotaDTO notaDTO);
}

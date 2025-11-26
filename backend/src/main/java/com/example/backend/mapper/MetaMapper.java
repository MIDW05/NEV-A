package com.example.backend.mapper;

import com.example.backend.dto.MetaDTO;
import com.example.backend.entity.MetaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MetaMapper {
    MetaMapper INSTANCE = Mappers.getMapper(MetaMapper.class);

    MetaDTO metaEntityAMetaDTO(MetaEntity metaEntity);
    MetaEntity metaDTOAMetaEntity(MetaDTO metaDTO);
}

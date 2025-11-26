package com.example.backend.mapper;

import com.example.backend.dto.MetaDTO;
import com.example.backend.entity.MetaEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MetaMapper {

    MetaMapper INSTANCE = Mappers.getMapper(MetaMapper.class);

    @Mappings({
            @Mapping(source = "usuario.id", target = "usuarioId")
    })
    MetaDTO metaEntityAMetaDTO(MetaEntity metaEntity);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "usuario", ignore = true)
    })
    MetaEntity metaDTOAMetaEntity(MetaDTO metaDTO);
}

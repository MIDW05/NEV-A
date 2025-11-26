package com.example.backend.mapper;

import com.example.backend.dto.TareaDTO;
import com.example.backend.entity.TareaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface TareaMapper {
    TareaMapper INSTANCE = Mappers.getMapper(TareaMapper.class);

    TareaDTO tareaEntityATareaDTO(TareaEntity tareaEntity);
    TareaEntity tareaDTOATareaEntity(TareaDTO tareaDTO);
}

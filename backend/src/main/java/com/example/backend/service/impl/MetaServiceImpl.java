package com.example.backend.service.impl;

import com.example.backend.dto.MetaDTO;
import com.example.backend.entity.MetaEntity;
import com.example.backend.mapper.MetaMapper;
import com.example.backend.repository.MetaRepository;
import com.example.backend.service.MetaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetaServiceImpl implements MetaService {

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private MetaMapper metaMapper;

    @Override
    public List<MetaDTO> listar() {
        List<MetaEntity> metas = metaRepository.findAll();
        List<MetaDTO> metaDTOs = new ArrayList<>();
        for (MetaEntity metaEntity : metas) {
            MetaDTO metaDTO = metaMapper.metaEntityAMetaDTO(metaEntity);
            metaDTOs.add(metaDTO);
        }
        return metaDTOs;
    }

    @Override
    @Transactional
    public MetaDTO guardar(MetaDTO metaDTO) {
        MetaEntity metaEntity = metaMapper.metaDTOAMetaEntity(metaDTO);
        return metaMapper.metaEntityAMetaDTO(metaRepository.save(metaEntity));
    }

    @Override
    public MetaDTO buscar(Long id) {
        MetaEntity metaEntity = metaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meta no encontrada"));
        return metaMapper.metaEntityAMetaDTO(metaEntity);
    }

    @Override
    public MetaDTO editar(MetaDTO metaDTO) {
        MetaEntity metaEntity = metaRepository.findById(metaDTO.getId()).get();
        metaEntity.setTitulo(metaDTO.getTitulo());
        metaEntity.setDescripcion(metaDTO.getDescripcion());
        metaEntity.setTipoMeta(metaDTO.getTipoMeta());
        metaEntity.setFechaLimite(metaDTO.getFechaLimite());
        MetaDTO metaDTO1 = metaMapper.metaEntityAMetaDTO(metaRepository.save(metaEntity));
        return metaDTO1;
    }

    @Override
    public void borrar(Long id) {
        metaRepository.deleteById(id);
    }
}

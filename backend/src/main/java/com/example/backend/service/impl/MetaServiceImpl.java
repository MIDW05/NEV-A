package com.example.backend.service.impl;

import com.example.backend.dto.MetaDTO;
import com.example.backend.entity.MetaEntity;
import com.example.backend.entity.UsuarioEntity;
import com.example.backend.mapper.MetaMapper;
import com.example.backend.repository.MetaRepository;
import com.example.backend.repository.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

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

        UsuarioEntity usuario = usuarioRepository.findById(metaDTO.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        metaEntity.setUsuario(usuario);

        return metaMapper.metaEntityAMetaDTO(metaRepository.save(metaEntity));
    }

    @Override
    public MetaDTO buscar(Long id) {
        MetaEntity metaEntity = metaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Meta no encontrada"));
        return metaMapper.metaEntityAMetaDTO(metaEntity);
    }

    @Override
    @Transactional
    public MetaDTO editar(MetaDTO metaDTO) {
        MetaEntity metaEntity = metaRepository.findById(metaDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Meta no encontrada"));

        metaEntity.setTitulo(metaDTO.getTitulo());
        metaEntity.setDescripcion(metaDTO.getDescripcion());
        metaEntity.setTipoMeta(metaDTO.getTipoMeta());
        metaEntity.setFechaLimite(metaDTO.getFechaLimite());

        if (metaDTO.getUsuarioId() != null &&
                (metaEntity.getUsuario() == null ||
                        !metaEntity.getUsuario().getId().equals(metaDTO.getUsuarioId()))) {

            UsuarioEntity usuario = usuarioRepository.findById(metaDTO.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            metaEntity.setUsuario(usuario);
        }

        MetaDTO metaDTO1 = metaMapper.metaEntityAMetaDTO(metaRepository.save(metaEntity));
        return metaDTO1;
    }

    @Override
    public void borrar(Long id) {
        metaRepository.deleteById(id);
    }
}

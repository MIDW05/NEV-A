package com.example.backend.service.impl;

import com.example.backend.dto.NotaDTO;
import com.example.backend.entity.NotaEntity;
import com.example.backend.mapper.NotaMapper;
import com.example.backend.repository.NotaRepository;
import com.example.backend.service.NotaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotaServiceImpl implements NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private NotaMapper notaMapper;

    @Override
    public List<NotaDTO> listar() {
        List<NotaEntity> notas = notaRepository.findAll();
        List<NotaDTO> notaDTOs = new ArrayList<>();
        for (NotaEntity notaEntity : notas) {
            NotaDTO notaDTO = notaMapper.notaEntityANotaDTO(notaEntity);
            notaDTOs.add(notaDTO);
        }
        return notaDTOs;
    }

    @Override
    @Transactional
    public NotaDTO guardar(NotaDTO notaDTO) {
        NotaEntity notaEntity = notaMapper.notaDTOANotaEntity(notaDTO);
        return notaMapper.notaEntityANotaDTO(notaRepository.save(notaEntity));
    }

    @Override
    public NotaDTO buscar(Long id) {
        NotaEntity notaEntity = notaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada"));
        return notaMapper.notaEntityANotaDTO(notaEntity);
    }

    @Override
    public NotaDTO editar(NotaDTO notaDTO) {
        NotaEntity notaEntity = notaRepository.findById(notaDTO.getId()).get();
        notaEntity.setTitulo(notaDTO.getTitulo());
        notaEntity.setCategoria(notaDTO.getCategoria());
        notaEntity.setContenido(notaDTO.getContenido());
        NotaDTO notaDTO1 = notaMapper.notaEntityANotaDTO(notaRepository.save(notaEntity));
        return notaDTO1;
    }

    @Override
    public void borrar(Long id) {
        notaRepository.deleteById(id);
    }
}

package com.example.backend.service.impl;

import com.example.backend.dto.NotaDTO;
import com.example.backend.entity.NotaEntity;
import com.example.backend.entity.TareaEntity;
import com.example.backend.entity.UsuarioEntity;
import com.example.backend.mapper.NotaMapper;
import com.example.backend.repository.NotaRepository;
import com.example.backend.repository.TareaRepository;
import com.example.backend.repository.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TareaRepository tareaRepository;

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

        // Usuario obligatorio
        UsuarioEntity usuario = usuarioRepository.findById(notaDTO.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        notaEntity.setUsuario(usuario);

        // Tarea opcional
        if (notaDTO.getTareaId() != null) {
            TareaEntity tarea = tareaRepository.findById(notaDTO.getTareaId())
                    .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
            notaEntity.setTarea(tarea);
        } else {
            notaEntity.setTarea(null);
        }

        return notaMapper.notaEntityANotaDTO(notaRepository.save(notaEntity));
    }

    @Override
    public NotaDTO buscar(Long id) {
        NotaEntity notaEntity = notaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada"));
        return notaMapper.notaEntityANotaDTO(notaEntity);
    }

    @Override
    @Transactional
    public NotaDTO editar(NotaDTO notaDTO) {
        NotaEntity notaEntity = notaRepository.findById(notaDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Nota no encontrada"));

        notaEntity.setTitulo(notaDTO.getTitulo());
        notaEntity.setCategoria(notaDTO.getCategoria());
        notaEntity.setContenido(notaDTO.getContenido());

        // Actualizar usuario si cambió
        if (notaDTO.getUsuarioId() != null &&
                (notaEntity.getUsuario() == null ||
                        !notaEntity.getUsuario().getId().equals(notaDTO.getUsuarioId()))) {

            UsuarioEntity usuario = usuarioRepository.findById(notaDTO.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            notaEntity.setUsuario(usuario);
        }

        // Actualizar tarea (o dejarla en null)
        if (notaDTO.getTareaId() != null) {
            TareaEntity tarea = tareaRepository.findById(notaDTO.getTareaId())
                    .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
            notaEntity.setTarea(tarea);
        } else {
            notaEntity.setTarea(null);
        }

        NotaDTO notaDTO1 = notaMapper.notaEntityANotaDTO(notaRepository.save(notaEntity));
        return notaDTO1;
    }

    @Override
    public void borrar(Long id) {
        notaRepository.deleteById(id);
    }
}

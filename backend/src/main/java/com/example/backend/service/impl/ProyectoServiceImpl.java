package com.example.backend.service.impl;

import com.example.backend.dto.ProyectoDTO;
import com.example.backend.entity.ProyectoEntity;
import com.example.backend.entity.UsuarioEntity;
import com.example.backend.mapper.ProyectoMapper;
import com.example.backend.repository.ProyectoRepository;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.service.ProyectoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProyectoMapper proyectoMapper;

    @Override
    public List<ProyectoDTO> listar() {
        List<ProyectoEntity> proyectos = proyectoRepository.findAll();
        List<ProyectoDTO> proyectoDTOs = new ArrayList<>();
        for (ProyectoEntity proyectoEntity : proyectos) {
            ProyectoDTO proyectoDTO = proyectoMapper.proyectoEntityAProyectoDTO(proyectoEntity);
            proyectoDTOs.add(proyectoDTO);
        }
        return proyectoDTOs;
    }

    @Override
    @Transactional
    public ProyectoDTO guardar(ProyectoDTO proyectoDTO) {
        ProyectoEntity proyectoEntity = proyectoMapper.proyectoDTOAProyectoEntity(proyectoDTO);

        UsuarioEntity usuario = usuarioRepository.findById(proyectoDTO.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        proyectoEntity.setUsuario(usuario);

        return proyectoMapper.proyectoEntityAProyectoDTO(proyectoRepository.save(proyectoEntity));
    }

    @Override
    public ProyectoDTO buscar(Long id) {
        ProyectoEntity proyectoEntity = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));
        return proyectoMapper.proyectoEntityAProyectoDTO(proyectoEntity);
    }

    @Override
    public ProyectoDTO editar(ProyectoDTO proyectoDTO) {
        ProyectoEntity proyectoEntity = proyectoRepository.findById(proyectoDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        proyectoEntity.setNombre(proyectoDTO.getNombre());
        proyectoEntity.setDescripcion(proyectoDTO.getDescripcion());

        if (proyectoDTO.getUsuarioId() != null &&
                (proyectoEntity.getUsuario() == null ||
                        !proyectoEntity.getUsuario().getId().equals(proyectoDTO.getUsuarioId()))) {

            UsuarioEntity usuario = usuarioRepository.findById(proyectoDTO.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
            proyectoEntity.setUsuario(usuario);
        }

        ProyectoDTO proyectoDTO1 =
                proyectoMapper.proyectoEntityAProyectoDTO(proyectoRepository.save(proyectoEntity));
        return proyectoDTO1;
    }

    @Override
    public void borrar(Long id) {
        proyectoRepository.deleteById(id);
    }
}

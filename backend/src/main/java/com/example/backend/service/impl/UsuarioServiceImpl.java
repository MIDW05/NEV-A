package com.example.backend.service.impl;

import com.example.backend.dto.UsuarioDTO;
import com.example.backend.entity.UsuarioEntity;
import com.example.backend.mapper.UsuarioMapper;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Override
    public List<UsuarioDTO> listar() {
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuarioDTOs = new ArrayList<>();
        for (UsuarioEntity usuarioEntity : usuarios) {
            UsuarioDTO usuarioDTO = usuarioMapper.usuarioEntityAUsDTO(usuarioEntity);
            usuarioDTOs.add(usuarioDTO);
        }
        return usuarioDTOs;
    }

    @Override
    @Transactional
    public UsuarioDTO guardar(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioEntity = usuarioMapper.usuarioDTOAUsEntity(usuarioDTO);
        return usuarioMapper.usuarioEntityAUsDTO(usuarioRepository.save(usuarioEntity));
    }

    @Override
    public UsuarioDTO buscar(Long id) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id).orElseThrow(() -> new
                EntityNotFoundException("Usuario no encontrada"));
        return usuarioMapper.usuarioEntityAUsDTO(usuarioEntity);

    }

    @Override
    public UsuarioDTO editar(UsuarioDTO usuarioDTO) {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(usuarioDTO.getId()).get();
        usuarioEntity.setNombre(usuarioDTO.getNombre());
        usuarioEntity.setEmail(usuarioDTO.getEmail());
        usuarioEntity.setContrasena(usuarioDTO.getContrasena());
        UsuarioDTO usuarioDTO1 = usuarioMapper.usuarioEntityAUsDTO(usuarioRepository.save(usuarioEntity));
        return usuarioDTO1;
    }

    @Override
    public void borrar(Long id) {usuarioRepository.deleteById(id);
    }
}

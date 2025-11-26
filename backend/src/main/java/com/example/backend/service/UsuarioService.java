package com.example.backend.service;

import com.example.backend.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
        public List<UsuarioDTO> listar();
        public UsuarioDTO guardar(UsuarioDTO usuarioDTO);
        public UsuarioDTO buscar(Long id);
        public UsuarioDTO editar(UsuarioDTO usuarioDTO);
        public void borrar(Long id);

}

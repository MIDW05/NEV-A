package com.example.backend.service;

import com.example.backend.dto.ProyectoDTO;
import java.util.List;

public interface ProyectoService {
    public List<ProyectoDTO> listar();
    public ProyectoDTO guardar(ProyectoDTO proyectoDTO);
    public ProyectoDTO buscar(Long id);
    public ProyectoDTO editar(ProyectoDTO proyectoDTO);
    public void borrar(Long id);
}

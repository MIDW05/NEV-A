package com.example.backend.service;

import com.example.backend.dto.MetaDTO;
import java.util.List;

public interface MetaService {
    public List<MetaDTO> listar();
    public MetaDTO guardar(MetaDTO metaDTO);
    public MetaDTO buscar(Long id);
    public MetaDTO editar(MetaDTO metaDTO);
    public void borrar(Long id);
}

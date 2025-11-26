package com.example.backend.service;

import com.example.backend.dto.NotaDTO;
import java.util.List;

public interface NotaService {
    public List<NotaDTO> listar();
    public NotaDTO guardar(NotaDTO notaDTO);
    public NotaDTO buscar(Long id);
    public NotaDTO editar(NotaDTO notaDTO);
    public void borrar(Long id);
}

package com.example.backend.service;

import com.example.backend.dto.HabitoDTO;
import java.util.List;

public interface HabitoService {
    public List<HabitoDTO> listar();
    public HabitoDTO guardar(HabitoDTO habitoDTO);
    public HabitoDTO buscar(Long id);
    public HabitoDTO editar(HabitoDTO habitoDTO);
    public void borrar(Long id);
}

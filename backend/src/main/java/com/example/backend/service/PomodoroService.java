package com.example.backend.service;

import com.example.backend.dto.PomodoroDTO;
import com.example.backend.enums.TipoIntervalo;

import java.util.List;
import java.util.Map;

public interface PomodoroService {
    PomodoroDTO iniciar(Long usuarioId, Long tareaId, TipoIntervalo tipo, Integer duracionMin);
    PomodoroDTO finalizar(Long sesionId);
    PomodoroDTO activo(Long usuarioId);
    List<PomodoroDTO> hoy(Long usuarioId);
    List<Map<String, Object>> planCicloEstandar(); // guía (8 pasos)
}

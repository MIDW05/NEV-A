package com.example.backend.service;

import com.example.backend.dto.PomodoroDTO;
import com.example.backend.enums.TipoIntervalo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PomodoroService {

    // CRUD base
    List<PomodoroDTO> listar();
    PomodoroDTO guardar(PomodoroDTO dto);
    List<PomodoroDTO> tareasPomodoro(Long tareaId);
    List<PomodoroDTO> usuarioPomodoros(Long usuarioId);

    // ✅ FIRMA NUEVA (la que usa tu Controller)
    PomodoroDTO iniciar(Long usuarioId, Long tareaId, TipoIntervalo tipo, Integer duracionMin);

    // ✅ Completar real
    PomodoroDTO completar(Long usuarioId, Long pomodoroId);

    // ✅ Sesión activa del usuario
    Optional<PomodoroDTO> activo(Long usuarioId);

    // ✅ Sesiones por día
    List<PomodoroDTO> sesionesDelDia(Long usuarioId, LocalDate fecha);

    // ✅ minutos enfoque por día
    Integer minutosEnfoqueDelDia(Long usuarioId, LocalDate fecha);
}

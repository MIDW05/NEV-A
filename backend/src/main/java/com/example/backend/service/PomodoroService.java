package com.example.backend.service;

import com.example.backend.dto.PomodoroDTO;

import java.util.List;

public interface PomodoroService {

    List<PomodoroDTO> listar();  // Obtener todas las sesiones Pomodoro

    PomodoroDTO guardar(PomodoroDTO pomodoroDTO);  // Guardar nueva sesión Pomodoro

    PomodoroDTO iniciar(Long id);  // Iniciar una sesión Pomodoro

    PomodoroDTO completar(Long id);  // Completar una sesión Pomodoro

    List<PomodoroDTO> tareasPomodoro(Long tareaId);  // Obtener Pomodoros por tarea

    List<PomodoroDTO> usuarioPomodoros(Long usuarioId);  // Obtener Pomodoros por usuario
}

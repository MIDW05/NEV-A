package com.example.backend.service;

import com.example.backend.dto.CalendarItemDTO;
import com.example.backend.dto.TareaDTO;

import java.util.Date;
import java.util.List;

public interface TareaService {
    List<TareaDTO> listar();
    TareaDTO guardar(TareaDTO tareaDTO);
    TareaDTO buscar(Long id);
    TareaDTO editar(TareaDTO tareaDTO);
    void borrar(Long id);

    // NUEVO (simples)
    List<TareaDTO> pendientes(Long usuarioId);
    List<TareaDTO> proximasAVencer(Long usuarioId, int dias);
    List<TareaDTO> recordatorios(Long usuarioId, int minutos);
    TareaDTO iniciar(Long tareaId);
    TareaDTO completar(Long tareaId);

    List<CalendarItemDTO> calendario(Long usuarioId, Date desde, Date hasta);

    // 🔗 Enlace tarea-proyecto
    TareaDTO asignarProyecto(Long tareaId, Long proyectoId);
    TareaDTO quitarProyecto(Long tareaId);
}

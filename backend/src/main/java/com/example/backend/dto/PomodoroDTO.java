package com.example.backend.dto;

import com.example.backend.enums.EstadoPomodoro;  // Usamos el enum EstadoPomodoro
import lombok.Data;

import java.util.Date;

@Data
public class PomodoroDTO {

    private Long id;

    private Long usuarioId;

    private Long tareaId; // Tarea opcional
    private Long proyectoId; // Proyecto opcional

    private Date inicio;
    private Date finPlanificado;
    private Date finReal;
    private Integer duracionMin;

    private EstadoPomodoro estado;  // Ahora usamos el enum EstadoPomodoro para el estado de la sesión
}

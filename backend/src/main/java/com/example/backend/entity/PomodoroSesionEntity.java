package com.example.backend.entity;

import com.example.backend.enums.EstadoPomodoro;  // Usamos este enum para el estado
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "pomodoro_sesiones")
public class PomodoroSesionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usamos el enum EstadoPomodoro para gestionar el estado de la sesión
    @NotNull(message = "El estado no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private EstadoPomodoro estado; // El estado es gestionado por este enum

    // Relación con la tarea (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarea_id")
    private TareaEntity tarea;

    // Relación con el proyecto (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id")
    private ProyectoEntity proyecto;

    // Fecha y hora de inicio
    @NotNull(message = "El inicio no puede ser nulo")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date inicio;

    // Fecha y hora de finalización planificada
    @Temporal(TemporalType.TIMESTAMP)
    private Date finPlanificado;

    // Fecha y hora de finalización real
    @Temporal(TemporalType.TIMESTAMP)
    private Date finReal;

    // Duración en minutos
    @NotNull(message = "La duración debe ser proporcionada")
    private Integer duracionMin;

    // Relación con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    // Generar los valores predeterminados
    @PrePersist
    public void prePersist() {
        if (estado == null) estado = EstadoPomodoro.PENDIENTE; // El estado predeterminado es "PENDIENTE"
    }
}

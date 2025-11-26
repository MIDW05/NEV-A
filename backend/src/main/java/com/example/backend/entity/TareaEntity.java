package com.example.backend.entity;

import com.example.backend.enums.EstadoTarea;
import com.example.backend.enums.Prioridad;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tareas")
public class TareaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El título no puede ser nulo")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    @Column(nullable = false)
    private String titulo;

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;

    @NotNull(message = "La prioridad no puede ser nula")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridad prioridad;

    @NotNull(message = "La fecha de vencimiento no puede ser nula")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaVencimiento;

    // 🔗 Tarea pertenece a un usuario
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    // 🔗 Tarea opcionalmente se asocia a un proyecto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id")
    private ProyectoEntity proyecto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTarea estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizacion;

    // duración para el calendario (en minutos)
    private Integer duracionEstimadaMinutos;

    @PrePersist
    public void prePersist() {
        if (fechaCreacion == null) fechaCreacion = new Date();
        if (estado == null) estado = EstadoTarea.PENDIENTE;
        if (duracionEstimadaMinutos == null) duracionEstimadaMinutos = 60;
    }
}

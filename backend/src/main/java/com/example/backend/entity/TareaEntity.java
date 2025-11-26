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

    @NotNull @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String titulo;

    @Size(max = 500)
    private String descripcion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridad prioridad;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fechaVencimiento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario; // <-- quitar el ";;" que tenías

    // NUEVO (sencillo)
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

    private Integer duracionEstimadaMinutos; // para el bloque de calendario

    @PrePersist
    public void prePersist() {
        if (fechaCreacion == null) fechaCreacion = new Date();
        if (estado == null) estado = EstadoTarea.PENDIENTE;
        if (duracionEstimadaMinutos == null) duracionEstimadaMinutos = 60; // 1h por defecto
    }
}

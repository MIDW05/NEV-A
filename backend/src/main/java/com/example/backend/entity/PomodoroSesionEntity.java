package com.example.backend.entity;

import com.example.backend.enums.TipoIntervalo;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "pomodoro_sesiones")
public class PomodoroSesionEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "tarea_id")
    private TareaEntity tarea; // opcional

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private TipoIntervalo tipo;

    @Temporal(TemporalType.TIMESTAMP) @Column(nullable = false)
    private Date inicio;

    @Temporal(TemporalType.TIMESTAMP) @Column(nullable = false)
    private Date finPlanificado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date finReal;

    @Column(nullable = false)
    private Integer duracionMin;

    @Column(nullable = false)
    private Boolean activo = true;
}

package com.example.backend.entity;

import com.example.backend.enums.TipoMeta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "metas")
public class MetaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nuevo: meta asociada a un usuario
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @NotNull(message = "El título no puede ser nulo")
    @Size(min = 3, max = 150, message = "El título debe tener entre 3 y 150 caracteres")
    @Column(nullable = false)
    private String titulo;

    @NotNull(message = "La descripción no puede ser nula")
    @Size(min = 3, max = 500, message = "La descripción debe tener entre 3 y 500 caracteres")
    @Column(nullable = false, length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMeta tipoMeta;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date fechaLimite;
}

package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "notas")
public class NotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El título no puede ser nulo")
    @Size(min = 3, max = 150, message = "El título debe tener entre 3 y 150 caracteres")
    @Column(nullable = false)
    private String titulo;

    @NotNull(message = "La categoría no puede ser nula")
    @Size(min = 1, max = 50, message = "La categoría debe tener entre 1 y 50 caracteres")
    @Column(nullable = false)
    private String categoria;

    @NotNull(message = "El contenido no puede ser nulo")
    @Size(min = 3, max = 1000, message = "El contenido debe tener entre 3 y 1000 caracteres")
    @Column(nullable = false, length = 1000)
    private String contenido;
}

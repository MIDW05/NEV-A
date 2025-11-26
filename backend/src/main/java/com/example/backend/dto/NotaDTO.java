package com.example.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NotaDTO {

    private Long id;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    // Opcional: permite enlazar la nota con una tarea concreta
    private Long tareaId;

    @NotNull(message = "El título no puede ser nulo")
    @Size(min = 3, max = 150, message = "El título debe tener entre 3 y 150 caracteres")
    private String titulo;

    @NotNull(message = "La categoría no puede ser nula")
    @Size(min = 1, max = 50, message = "La categoría debe tener entre 1 y 50 caracteres")
    private String categoria;

    @NotNull(message = "El contenido no puede ser nulo")
    @Size(min = 3, max = 1000, message = "El contenido debe tener entre 3 y 1000 caracteres")
    private String contenido;
}

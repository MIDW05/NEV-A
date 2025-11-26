package com.example.backend.dto;

import com.example.backend.enums.TipoMeta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class MetaDTO {
    private Long id;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El título no puede ser nulo")
    @Size(min = 3, max = 150, message = "El título debe tener entre 3 y 150 caracteres")
    private String titulo;

    @NotNull(message = "La descripción no puede ser nula")
    @Size(min = 3, max = 500, message = "La descripción debe tener entre 3 y 500 caracteres")
    private String descripcion;

    private TipoMeta tipoMeta;
    private Date fechaLimite;
}

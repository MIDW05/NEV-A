package com.example.backend.dto;

import com.example.backend.enums.Prioridad;
import com.example.backend.enums.EstadoTarea;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class TareaDTO {

    private Long id;

    @NotNull @Size(min = 3, max = 150)
    private String titulo;

    @Size(max = 500)
    private String descripcion;

    @NotNull
    private Prioridad prioridad;

    @NotNull
    private Date fechaVencimiento;

    private UsuarioDTO usuario;

    private EstadoTarea estado;
    private Date fechaCreacion;
    private Date fechaInicio;
    private Date fechaFinalizacion;
    private Integer duracionEstimadaMinutos;
}

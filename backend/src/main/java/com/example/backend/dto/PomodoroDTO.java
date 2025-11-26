package com.example.backend.dto;

import com.example.backend.enums.TipoIntervalo;
import lombok.Data;
import java.util.Date;

@Data
public class PomodoroDTO {
    private Long id;
    private Long usuarioId;
    private Long tareaId;
    private TipoIntervalo tipo;
    private Date inicio;
    private Date finPlanificado;
    private Date finReal;
    private Integer duracionMin;
    private Boolean activo;
}

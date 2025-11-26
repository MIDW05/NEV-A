package com.example.backend.dto;

import com.example.backend.enums.EstadoTarea;
import lombok.Data;
import java.util.Date;

@Data
public class CalendarItemDTO {
    private Long tareaId;
    private String titulo;
    private Date inicio;
    private Date fin;
    private EstadoTarea estado;
}

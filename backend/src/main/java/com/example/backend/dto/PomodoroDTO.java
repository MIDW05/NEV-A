package com.example.backend.dto;

import com.example.backend.enums.EstadoPomodoro;
import com.example.backend.enums.TipoIntervalo;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PomodoroDTO {

    private Long id;

    private Long usuarioId;

    private Long tareaId;

    // ✅ nombre real que casi seguro tienes en tu entity/mapper
    private TipoIntervalo tipoIntervalo;

    private Integer duracionMin;

    private EstadoPomodoro estado;

    private Date inicio;

    private Date finPlanificado;

    private Date finReal;

    public TipoIntervalo getTipo() {
        return this.tipoIntervalo;
    }

    public void setTipo(TipoIntervalo tipo) {
        this.tipoIntervalo = tipo;
    }
}

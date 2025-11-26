package com.example.backend.repository;

import com.example.backend.entity.TareaEntity;
import com.example.backend.enums.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TareaRepository extends JpaRepository<TareaEntity, Long> {

    List<TareaEntity> findByUsuarioIdAndEstadoOrderByFechaVencimientoAsc(Long usuarioId, EstadoTarea estado);

    List<TareaEntity> findByUsuarioIdAndEstadoInOrderByFechaVencimientoAsc(Long usuarioId, List<EstadoTarea> estados);

    List<TareaEntity> findByUsuarioIdAndEstadoInAndFechaVencimientoBetweenOrderByFechaVencimientoAsc(
            Long usuarioId, List<EstadoTarea> estados, Date desde, Date hasta);
}

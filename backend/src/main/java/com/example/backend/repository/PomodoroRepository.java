package com.example.backend.repository;

import com.example.backend.entity.PomodoroSesionEntity;
import com.example.backend.enums.EstadoPomodoro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PomodoroRepository extends JpaRepository<PomodoroSesionEntity, Long> {

    List<PomodoroSesionEntity> findByUsuarioId(Long usuarioId);

    List<PomodoroSesionEntity> findByTareaId(Long tareaId);

    Optional<PomodoroSesionEntity> findFirstByUsuarioIdAndEstado(Long usuarioId, EstadoPomodoro estado);

    List<PomodoroSesionEntity> findByUsuarioIdAndInicioBetween(Long usuarioId, Date desde, Date hasta);
}

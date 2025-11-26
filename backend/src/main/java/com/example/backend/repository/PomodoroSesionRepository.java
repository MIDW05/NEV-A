package com.example.backend.repository;

import com.example.backend.entity.PomodoroSesionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PomodoroSesionRepository extends JpaRepository<PomodoroSesionEntity, Long> {
    Optional<PomodoroSesionEntity> findFirstByUsuarioIdAndActivoTrueOrderByInicioDesc(Long usuarioId);
    List<PomodoroSesionEntity> findByUsuarioIdAndInicioBetweenOrderByInicioAsc(Long usuarioId, Date desde, Date hasta);
}

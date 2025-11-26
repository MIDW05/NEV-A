package com.example.backend.repository;

import com.example.backend.entity.PomodoroSesionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PomodoroRepository extends JpaRepository<PomodoroSesionEntity, Long> {

    // Buscar pomodoros por tareaId
    List<PomodoroSesionEntity> findByTareaId(Long tareaId);

    // Buscar pomodoros por usuarioId
    List<PomodoroSesionEntity> findByUsuarioId(Long usuarioId);
}

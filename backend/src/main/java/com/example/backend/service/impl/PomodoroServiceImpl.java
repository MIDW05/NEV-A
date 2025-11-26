package com.example.backend.service.impl;

import com.example.backend.dto.PomodoroDTO;
import com.example.backend.entity.PomodoroSesionEntity;
import com.example.backend.entity.TareaEntity;
import com.example.backend.entity.UsuarioEntity;
import com.example.backend.enums.EstadoPomodoro;
import com.example.backend.enums.TipoIntervalo;
import com.example.backend.mapper.PomodoroMapper;
import com.example.backend.repository.PomodoroRepository;
import com.example.backend.repository.TareaRepository;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.service.PomodoroService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PomodoroServiceImpl implements PomodoroService {

    @Autowired private PomodoroRepository pomodoroRepository;
    @Autowired private TareaRepository tareaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PomodoroMapper pomodoroMapper;

    // ===== CRUD =====
    @Override
    public List<PomodoroDTO> listar() {
        return pomodoroRepository.findAll().stream()
                .map(pomodoroMapper::pomodoroEntityAPomodoroDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PomodoroDTO guardar(PomodoroDTO dto) {
        PomodoroSesionEntity ent = pomodoroMapper.pomodoroDTOAPomodoroEntity(dto);

        UsuarioEntity usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        TareaEntity tarea = null;
        if (dto.getTareaId() != null) {
            tarea = tareaRepository.findById(dto.getTareaId())
                    .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        }

        ent.setUsuario(usuario);
        ent.setTarea(tarea);

        if (ent.getEstado() == null) ent.setEstado(EstadoPomodoro.ACTIVO);
        if (ent.getInicio() == null) ent.setInicio(new Date());

        return pomodoroMapper.pomodoroEntityAPomodoroDTO(pomodoroRepository.save(ent));
    }

    // ✅ ===== INICIAR REAL =====
    @Override
    @Transactional
    public PomodoroDTO iniciar(Long usuarioId, Long tareaId, TipoIntervalo tipo, Integer duracionMin) {

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Si ya hay activo, no creamos otro
        Optional<PomodoroSesionEntity> activo = pomodoroRepository
                .findFirstByUsuarioIdAndEstado(usuarioId, EstadoPomodoro.ACTIVO);

        if (activo.isPresent()) {
            throw new IllegalStateException("Ya existe un Pomodoro activo para este usuario");
        }

        TareaEntity tarea = null;
        if (tareaId != null) {
            tarea = tareaRepository.findById(tareaId)
                    .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        }

        PomodoroSesionEntity nuevo = new PomodoroSesionEntity();
        nuevo.setUsuario(usuario);
        nuevo.setTarea(tarea);
        nuevo.setTipo(tipo);
        nuevo.setDuracionMin(duracionMin);

        Date inicio = new Date();
        nuevo.setInicio(inicio);
        nuevo.setEstado(EstadoPomodoro.ACTIVO);

        Instant finPlan = inicio.toInstant().plus(Duration.ofMinutes(duracionMin));
        nuevo.setFinPlanificado(Date.from(finPlan));

        return pomodoroMapper.pomodoroEntityAPomodoroDTO(
                pomodoroRepository.save(nuevo)
        );
    }

    // ✅ ===== COMPLETAR REAL =====
    @Override
    @Transactional
    public PomodoroDTO completar(Long usuarioId, Long pomodoroId) {

        PomodoroSesionEntity pomodoro = pomodoroRepository.findById(pomodoroId)
                .orElseThrow(() -> new EntityNotFoundException("Pomodoro no encontrado"));

        if (!pomodoro.getUsuario().getId().equals(usuarioId)) {
            throw new IllegalStateException("Este Pomodoro no pertenece al usuario");
        }

        pomodoro.setEstado(EstadoPomodoro.COMPLETADO);
        pomodoro.setFinReal(new Date());

        return pomodoroMapper.pomodoroEntityAPomodoroDTO(
                pomodoroRepository.save(pomodoro)
        );
    }

    // ✅ ===== ACTIVO =====
    @Override
    public Optional<PomodoroDTO> activo(Long usuarioId) {
        return pomodoroRepository.findFirstByUsuarioIdAndEstado(usuarioId, EstadoPomodoro.ACTIVO)
                .map(pomodoroMapper::pomodoroEntityAPomodoroDTO);
    }

    // ✅ ===== SESIONES DEL DÍA =====
    @Override
    public List<PomodoroDTO> sesionesDelDia(Long usuarioId, LocalDate fecha) {

        LocalDateTime start = fecha.atStartOfDay();
        LocalDateTime end = fecha.plusDays(1).atStartOfDay();

        Date desde = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
        Date hasta = Date.from(end.atZone(ZoneId.systemDefault()).toInstant());

        return pomodoroRepository.findByUsuarioIdAndInicioBetween(usuarioId, desde, hasta)
                .stream()
                .map(pomodoroMapper::pomodoroEntityAPomodoroDTO)
                .collect(Collectors.toList());
    }

    // ✅ ===== MINUTOS ENFOQUE DEL DÍA =====
    @Override
    public Integer minutosEnfoqueDelDia(Long usuarioId, LocalDate fecha) {
        return sesionesDelDia(usuarioId, fecha).stream()
                .filter(p -> p.getTipo() == TipoIntervalo.ENFOQUE)
                .mapToInt(PomodoroDTO::getDuracionMin)
                .sum();
    }

    // ===== CONSULTAS =====
    @Override
    public List<PomodoroDTO> tareasPomodoro(Long tareaId) {
        return pomodoroRepository.findByTareaId(tareaId)
                .stream()
                .map(pomodoroMapper::pomodoroEntityAPomodoroDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PomodoroDTO> usuarioPomodoros(Long usuarioId) {
        return pomodoroRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(pomodoroMapper::pomodoroEntityAPomodoroDTO)
                .collect(Collectors.toList());
    }
}

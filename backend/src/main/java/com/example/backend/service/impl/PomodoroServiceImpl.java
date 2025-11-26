package com.example.backend.service.impl;

import com.example.backend.dto.PomodoroDTO;
import com.example.backend.entity.PomodoroSesionEntity;
import com.example.backend.entity.TareaEntity;
import com.example.backend.entity.UsuarioEntity;
import com.example.backend.enums.EstadoPomodoro;
import com.example.backend.mapper.PomodoroMapper;
import com.example.backend.repository.PomodoroRepository;
import com.example.backend.repository.TareaRepository;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.service.PomodoroService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PomodoroServiceImpl implements PomodoroService {

    @Autowired
    private PomodoroRepository pomodoroRepository;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PomodoroMapper pomodoroMapper;

    @Override
    public List<PomodoroDTO> listar() {
        // Asegúrate de que nunca sea nulo
        return pomodoroRepository.findAll().stream()
                .map(pomodoroMapper::pomodoroEntityAPomodoroDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PomodoroDTO guardar(PomodoroDTO pomodoroDTO) {
        PomodoroSesionEntity pomodoroEntity = pomodoroMapper.pomodoroDTOAPomodoroEntity(pomodoroDTO);

        UsuarioEntity usuario = usuarioRepository.findById(pomodoroDTO.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        TareaEntity tarea = null;
        if (pomodoroDTO.getTareaId() != null) {
            tarea = tareaRepository.findById(pomodoroDTO.getTareaId())
                    .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        }

        pomodoroEntity.setUsuario(usuario);
        pomodoroEntity.setTarea(tarea);

        return pomodoroMapper.pomodoroEntityAPomodoroDTO(pomodoroRepository.save(pomodoroEntity));
    }

    @Override
    public PomodoroDTO iniciar(Long id) {
        PomodoroSesionEntity pomodoro = pomodoroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pomodoro no encontrado"));

        pomodoro.setEstado(EstadoPomodoro.ACTIVO);
        pomodoro.setInicio(new Date());
        return pomodoroMapper.pomodoroEntityAPomodoroDTO(pomodoroRepository.save(pomodoro));
    }

    @Override
    public PomodoroDTO completar(Long id) {
        PomodoroSesionEntity pomodoro = pomodoroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pomodoro no encontrado"));

        pomodoro.setEstado(EstadoPomodoro.COMPLETADO);
        pomodoro.setFinReal(new Date());
        return pomodoroMapper.pomodoroEntityAPomodoroDTO(pomodoroRepository.save(pomodoro));
    }

    @Override
    public List<PomodoroDTO> tareasPomodoro(Long tareaId) {
        // Usamos findByTareaId() y devolvemos una lista vacía si no hay resultados
        List<PomodoroSesionEntity> pomodoros = pomodoroRepository.findByTareaId(tareaId);
        return pomodoros.isEmpty() ? List.of() : pomodoros.stream()
                .map(pomodoroMapper::pomodoroEntityAPomodoroDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PomodoroDTO> usuarioPomodoros(Long usuarioId) {
        // Usamos findByUsuarioId() y devolvemos una lista vacía si no hay resultados
        List<PomodoroSesionEntity> pomodoros = pomodoroRepository.findByUsuarioId(usuarioId);
        return pomodoros.isEmpty() ? List.of() : pomodoros.stream()
                .map(pomodoroMapper::pomodoroEntityAPomodoroDTO)
                .collect(Collectors.toList());
    }
}

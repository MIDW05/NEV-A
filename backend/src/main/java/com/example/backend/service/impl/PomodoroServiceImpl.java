package com.example.backend.service.impl;

import com.example.backend.dto.PomodoroDTO;
import com.example.backend.entity.PomodoroSesionEntity;
import com.example.backend.entity.TareaEntity;
import com.example.backend.entity.UsuarioEntity;
import com.example.backend.enums.TipoIntervalo;
import com.example.backend.mapper.PomodoroMapper;
import com.example.backend.repository.PomodoroSesionRepository;
import com.example.backend.repository.TareaRepository;
import com.example.backend.repository.UsuarioRepository;
import com.example.backend.service.PomodoroService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PomodoroServiceImpl implements PomodoroService {

    private static final int FOCUS = 25, SHORT = 5, LONG = 15;

    @Autowired private PomodoroSesionRepository repo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private TareaRepository tareaRepo;
    @Autowired private PomodoroMapper mapper;

    @Override
    @Transactional
    public PomodoroDTO iniciar(Long usuarioId, Long tareaId, TipoIntervalo tipo, Integer duracionMin) {
        UsuarioEntity usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        TareaEntity tarea = (tareaId != null) ? tareaRepo.findById(tareaId).orElse(null) : null;

        int dur = (duracionMin != null) ? duracionMin :
                (tipo == TipoIntervalo.ENFOQUE ? FOCUS :
                        tipo == TipoIntervalo.DESCANSO_CORTO ? SHORT : LONG);

        Date inicio = new Date();
        Date finPlan = new Date(inicio.getTime() + dur * 60L * 1000L);

        PomodoroSesionEntity e = new PomodoroSesionEntity();
        e.setUsuario(usuario);
        e.setTarea(tarea);
        e.setTipo(tipo);
        e.setInicio(inicio);
        e.setFinPlanificado(finPlan);
        e.setDuracionMin(dur);
        e.setActivo(true);

        e = repo.save(e);
        return mapper.entityToDto(e);
    }

    @Override
    @Transactional
    public PomodoroDTO finalizar(Long sesionId) {
        PomodoroSesionEntity e = repo.findById(sesionId)
                .orElseThrow(() -> new EntityNotFoundException("Sesión no encontrada"));
        e.setActivo(false);
        e.setFinReal(new Date());
        return mapper.entityToDto(repo.save(e));
    }

    @Override
    public PomodoroDTO activo(Long usuarioId) {
        return repo.findFirstByUsuarioIdAndActivoTrueOrderByInicioDesc(usuarioId)
                .map(mapper::entityToDto).orElse(null);
    }

    @Override
    public List<PomodoroDTO> hoy(Long usuarioId) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0); c.set(Calendar.MILLISECOND, 0);
        Date desde = c.getTime();
        c.add(Calendar.DATE, 1);
        Date hasta = c.getTime();

        return repo.findByUsuarioIdAndInicioBetweenOrderByInicioAsc(usuarioId, desde, hasta)
                .stream().map(mapper::entityToDto).toList();
    }

    @Override
    public List<Map<String, Object>> planCicloEstandar() {
        // (25/5) x3 + (25/15) => 8 pasos
        return List.of(
                paso(TipoIntervalo.ENFOQUE, 25, 1),
                paso(TipoIntervalo.DESCANSO_CORTO, 5, 2),
                paso(TipoIntervalo.ENFOQUE, 25, 3),
                paso(TipoIntervalo.DESCANSO_CORTO, 5, 4),
                paso(TipoIntervalo.ENFOQUE, 25, 5),
                paso(TipoIntervalo.DESCANSO_CORTO, 5, 6),
                paso(TipoIntervalo.ENFOQUE, 25, 7),
                paso(TipoIntervalo.DESCANSO_LARGO, 15, 8)
        );
    }

    private Map<String, Object> paso(TipoIntervalo t, int min, int orden) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("orden", orden);
        m.put("tipo", t);
        m.put("minutos", min);
        return m;
    }
}

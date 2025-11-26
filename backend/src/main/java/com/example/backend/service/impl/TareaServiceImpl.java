package com.example.backend.service.impl;

import com.example.backend.dto.CalendarItemDTO;
import com.example.backend.dto.TareaDTO;
import com.example.backend.entity.TareaEntity;
import com.example.backend.enums.EstadoTarea;
import com.example.backend.mapper.TareaMapper;
import com.example.backend.repository.TareaRepository;
import com.example.backend.service.TareaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TareaServiceImpl implements TareaService {

    @Autowired private TareaRepository tareaRepository;
    @Autowired private TareaMapper tareaMapper;

    @Override
    public List<TareaDTO> listar() {
        return tareaRepository.findAll().stream()
                .map(tareaMapper::tareaEntityATareaDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TareaDTO guardar(TareaDTO tareaDTO) {
        TareaEntity e = tareaMapper.tareaDTOATareaEntity(tareaDTO);
        return tareaMapper.tareaEntityATareaDTO(tareaRepository.save(e));
    }

    @Override
    public TareaDTO buscar(Long id) {
        TareaEntity e = tareaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        return tareaMapper.tareaEntityATareaDTO(e);
    }

    @Override
    public TareaDTO editar(TareaDTO tareaDTO) {
        TareaEntity e = tareaRepository.findById(tareaDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        e.setTitulo(tareaDTO.getTitulo());
        e.setDescripcion(tareaDTO.getDescripcion());
        e.setPrioridad(tareaDTO.getPrioridad());
        e.setFechaVencimiento(tareaDTO.getFechaVencimiento());
        e.setDuracionEstimadaMinutos(tareaDTO.getDuracionEstimadaMinutos());
        return tareaMapper.tareaEntityATareaDTO(tareaRepository.save(e));
    }

    @Override
    public void borrar(Long id) {
        tareaRepository.deleteById(id);
    }

    // ------- NUEVO --------

    private void actualizarVencidasParaUsuario(Long usuarioId) {
        Date ahora = new Date();
        List<TareaEntity> todas = tareaRepository.findByUsuarioIdAndEstadoInOrderByFechaVencimientoAsc(
                usuarioId, Arrays.asList(EstadoTarea.PENDIENTE, EstadoTarea.EN_CURSO, EstadoTarea.VENCIDA));
        boolean cambios = false;
        for (TareaEntity t : todas) {
            if (t.getEstado() != EstadoTarea.COMPLETADA &&
                    t.getFechaVencimiento() != null &&
                    t.getFechaVencimiento().before(ahora)) {
                if (t.getEstado() != EstadoTarea.VENCIDA) {
                    t.setEstado(EstadoTarea.VENCIDA);
                    cambios = true;
                }
            }
        }
        if (cambios) tareaRepository.saveAll(todas);
    }

    @Override
    public List<TareaDTO> pendientes(Long usuarioId) {
        actualizarVencidasParaUsuario(usuarioId);
        return tareaRepository
                .findByUsuarioIdAndEstadoInOrderByFechaVencimientoAsc(usuarioId,
                        Arrays.asList(EstadoTarea.PENDIENTE, EstadoTarea.EN_CURSO))
                .stream().map(tareaMapper::tareaEntityATareaDTO).collect(Collectors.toList());
    }

    @Override
    public List<TareaDTO> proximasAVencer(Long usuarioId, int dias) {
        actualizarVencidasParaUsuario(usuarioId);
        Date ahora = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(ahora);
        Date desde = cal.getTime();
        cal.add(Calendar.DATE, dias);
        Date hasta = cal.getTime();

        return tareaRepository
                .findByUsuarioIdAndEstadoInAndFechaVencimientoBetweenOrderByFechaVencimientoAsc(
                        usuarioId, Arrays.asList(EstadoTarea.PENDIENTE, EstadoTarea.EN_CURSO), desde, hasta)
                .stream().map(tareaMapper::tareaEntityATareaDTO).collect(Collectors.toList());
    }

    @Override
    public List<TareaDTO> recordatorios(Long usuarioId, int minutos) {
        actualizarVencidasParaUsuario(usuarioId);
        Date ahora = new Date();
        Date hasta = new Date(ahora.getTime() + minutos * 60L * 1000L);
        return tareaRepository
                .findByUsuarioIdAndEstadoInAndFechaVencimientoBetweenOrderByFechaVencimientoAsc(
                        usuarioId, Arrays.asList(EstadoTarea.PENDIENTE, EstadoTarea.EN_CURSO), ahora, hasta)
                .stream().map(tareaMapper::tareaEntityATareaDTO).collect(Collectors.toList());
    }

    @Override
    public TareaDTO iniciar(Long tareaId) {
        TareaEntity e = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        if (e.getEstado() == EstadoTarea.COMPLETADA) return tareaMapper.tareaEntityATareaDTO(e);
        e.setEstado(EstadoTarea.EN_CURSO);
        if (e.getFechaInicio() == null) e.setFechaInicio(new Date());
        return tareaMapper.tareaEntityATareaDTO(tareaRepository.save(e));
    }

    @Override
    public TareaDTO completar(Long tareaId) {
        TareaEntity e = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        e.setEstado(EstadoTarea.COMPLETADA);
        e.setFechaFinalizacion(new Date());
        return tareaMapper.tareaEntityATareaDTO(tareaRepository.save(e));
    }

    @Override
    public List<CalendarItemDTO> calendario(Long usuarioId, Date desde, Date hasta) {
        actualizarVencidasParaUsuario(usuarioId);
        List<TareaEntity> tareas = tareaRepository
                .findByUsuarioIdAndEstadoInAndFechaVencimientoBetweenOrderByFechaVencimientoAsc(
                        usuarioId,
                        Arrays.asList(EstadoTarea.PENDIENTE, EstadoTarea.EN_CURSO, EstadoTarea.COMPLETADA, EstadoTarea.VENCIDA),
                        desde, hasta);
        List<CalendarItemDTO> out = new ArrayList<>();
        for (TareaEntity t : tareas) {
            CalendarItemDTO c = new CalendarItemDTO();
            c.setTareaId(t.getId());
            c.setTitulo(t.getTitulo());
            c.setEstado(t.getEstado());
            Date inicio = t.getFechaVencimiento();
            c.setInicio(inicio);
            Calendar cal = Calendar.getInstance();
            cal.setTime(inicio);
            cal.add(Calendar.MINUTE, t.getDuracionEstimadaMinutos() == null ? 60 : t.getDuracionEstimadaMinutos());
            c.setFin(cal.getTime());
            out.add(c);
        }
        return out;
    }
}

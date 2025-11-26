package com.example.backend.service.impl;

import com.example.backend.entity.TareaEntity;
import com.example.backend.enums.EstadoTarea;
import com.example.backend.repository.TareaRepository;
import com.example.backend.service.CalendarService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired private TareaRepository tareaRepository;

    // ICS usa formato UTC "yyyyMMdd'T'HHmmss'Z'"
    private static final SimpleDateFormat ICS_FMT = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
    static { ICS_FMT.setTimeZone(TimeZone.getTimeZone("UTC")); }

    @Override
    public String buildIcsForUsuario(Long usuarioId, int minutosBloqueEvento) {
        // Trae PENDIENTE/EN_PROGRESO
        Date ahora = new Date();
        Date hasta = new Date(ahora.getTime() + 365L * 24 * 60 * 60 * 1000); // 1 año vista
        List<TareaEntity> tareas = tareaRepository.findByUsuarioIdAndEstadoInAndFechaVencimientoBetweenOrderByFechaVencimientoAsc(
                usuarioId, List.of(EstadoTarea.PENDIENTE, EstadoTarea.EN_CURSO),
                new Date(0), hasta);

        StringBuilder sb = new StringBuilder();
        sb.append("BEGIN:VCALENDAR\r\nVERSION:2.0\r\nPRODID:-//LP2//StudentTasks//ES\r\n");

        for (TareaEntity t : tareas) {
            Date start = t.getFechaVencimiento();
            Date end = new Date(start.getTime() + (long) minutosBloqueEvento * 60 * 1000);
            String uid = "tarea-" + t.getId() + "@studenttasks";

            sb.append("BEGIN:VEVENT\r\n");
            sb.append("UID:").append(uid).append("\r\n");
            sb.append("DTSTAMP:").append(ICS_FMT.format(new Date())).append("\r\n");
            sb.append("DTSTART:").append(ICS_FMT.format(start)).append("\r\n");
            sb.append("DTEND:").append(ICS_FMT.format(end)).append("\r\n");
            sb.append("SUMMARY:").append(escape(t.getTitulo())).append("\r\n");
            if (t.getDescripcion() != null) {
                sb.append("DESCRIPTION:").append(escape(t.getDescripcion())).append("\r\n");
            }
            sb.append("END:VEVENT\r\n");
        }
        sb.append("END:VCALENDAR\r\n");
        return sb.toString();
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace(",", "\\,").replace(";", "\\;").replace("\n", "\\n");
    }
}

package com.example.backend.controller;

import com.example.backend.dto.PomodoroDTO;
import com.example.backend.enums.TipoIntervalo;
import com.example.backend.service.PomodoroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pomodoro")
@CrossOrigin(origins = "*")
public class PomodoroController {

    @Autowired
    private PomodoroService pomodoroService;

    @PostMapping("/iniciar")
    public PomodoroDTO iniciar(@RequestParam Long usuarioId,
                               @RequestParam(required = false) Long tareaId,
                               @RequestParam TipoIntervalo tipo,
                               @RequestParam Integer duracionMin) {
        return pomodoroService.iniciar(usuarioId, tareaId, tipo, duracionMin);
    }

    @PostMapping("/{pomodoroId}/completar")
    public PomodoroDTO completar(@RequestParam Long usuarioId,
                                 @PathVariable Long pomodoroId) {
        return pomodoroService.completar(usuarioId, pomodoroId);
    }

    @GetMapping("/activo")
    public PomodoroDTO activo(@RequestParam Long usuarioId) {
        return pomodoroService.activo(usuarioId).orElse(null);
    }

    @GetMapping("/hoy")
    public List<PomodoroDTO> hoy(@RequestParam Long usuarioId) {
        return pomodoroService.sesionesDelDia(usuarioId, LocalDate.now());
    }

    @GetMapping("/hoy/minutos-enfoque")
    public Integer minutosEnfoqueHoy(@RequestParam Long usuarioId) {
        return pomodoroService.minutosEnfoqueDelDia(usuarioId, LocalDate.now());
    }

    // CRUD opcional
    @GetMapping
    public List<PomodoroDTO> listar() {
        return pomodoroService.listar();
    }
}

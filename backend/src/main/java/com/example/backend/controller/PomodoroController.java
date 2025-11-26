package com.example.backend.controller;

import com.example.backend.dto.PomodoroDTO;
import com.example.backend.enums.TipoIntervalo;
import com.example.backend.service.PomodoroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pomodoro")
public class PomodoroController {

    @Autowired private PomodoroService pomodoroService;

    @PostMapping("/iniciar")
    public ResponseEntity<PomodoroDTO> iniciar(@RequestParam Long usuarioId,
                                               @RequestParam(required = false) Long tareaId,
                                               @RequestParam TipoIntervalo tipo,
                                               @RequestParam(required = false) Integer duracionMin) {
        return ResponseEntity.ok(pomodoroService.iniciar(usuarioId, tareaId, tipo, duracionMin));
    }

    @PostMapping("/{sesionId}/finalizar")
    public ResponseEntity<PomodoroDTO> finalizar(@PathVariable Long sesionId) {
        return ResponseEntity.ok(pomodoroService.finalizar(sesionId));
    }

    @GetMapping("/activo")
    public ResponseEntity<PomodoroDTO> activo(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(pomodoroService.activo(usuarioId));
    }

    @GetMapping("/hoy")
    public ResponseEntity<List<PomodoroDTO>> hoy(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(pomodoroService.hoy(usuarioId));
    }

    @GetMapping("/plan")
    public ResponseEntity<List<Map<String, Object>>> plan() {
        return ResponseEntity.ok(pomodoroService.planCicloEstandar());
    }
}

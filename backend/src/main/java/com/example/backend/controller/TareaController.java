package com.example.backend.controller;

import com.example.backend.dto.TareaDTO;
import com.example.backend.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.dto.CalendarItemDTO;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tareas")
public class TareaController {
    @Autowired
    private TareaService tareaService;

    @GetMapping
    public ResponseEntity<List<TareaDTO>> listar() {
        return new ResponseEntity<>(tareaService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TareaDTO> guardar(@Valid @RequestBody TareaDTO tareaDTO) {
        return new ResponseEntity<>(tareaService.guardar(tareaDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaDTO> buscar(@PathVariable Long id) {
        return new ResponseEntity<>(tareaService.buscar(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TareaDTO> editar(@Valid @RequestBody TareaDTO tareaDTO) {
        return new ResponseEntity<>(tareaService.editar(tareaDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        tareaService.borrar(id);
        return new ResponseEntity<>("Eliminado", HttpStatus.OK);
    }



    @GetMapping("/pendientes")
    public ResponseEntity<List<TareaDTO>> pendientes(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(tareaService.pendientes(usuarioId));
    }

    @GetMapping("/proximas")
    public ResponseEntity<List<TareaDTO>> proximas(@RequestParam Long usuarioId,
                                                   @RequestParam(defaultValue = "3") int dias) {
        return ResponseEntity.ok(tareaService.proximasAVencer(usuarioId, dias));
    }

    @GetMapping("/recordatorios")
    public ResponseEntity<List<TareaDTO>> recordatorios(@RequestParam Long usuarioId,
                                                        @RequestParam(defaultValue = "60") int minutos) {
        return ResponseEntity.ok(tareaService.recordatorios(usuarioId, minutos));
    }

    @PostMapping("/{id}/iniciar")
    public ResponseEntity<TareaDTO> iniciar(@PathVariable Long id) {
        return ResponseEntity.ok(tareaService.iniciar(id));
    }

    @PostMapping("/{id}/completar")
    public ResponseEntity<TareaDTO> completar(@PathVariable Long id) {
        return ResponseEntity.ok(tareaService.completar(id));
    }

    @GetMapping("/calendario")
    public ResponseEntity<List<CalendarItemDTO>> calendario(@RequestParam Long usuarioId,
                                                            @RequestParam String desde, // yyyy-MM-dd
                                                            @RequestParam String hasta) { // yyyy-MM-dd
        Date d1 = parse(desde + " 00:00:00");
        Date d2 = parse(hasta + " 23:59:59");
        return ResponseEntity.ok(tareaService.calendario(usuarioId, d1, d2));
    }

    private Date parse(String s) {
        try {
            return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fecha inválida, usa yyyy-MM-dd");
        }
    }

}

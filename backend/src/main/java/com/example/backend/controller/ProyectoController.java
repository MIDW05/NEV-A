package com.example.backend.controller;

import com.example.backend.dto.ProyectoDTO;
import com.example.backend.service.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<ProyectoDTO>> listar() {
        return new ResponseEntity<>(proyectoService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProyectoDTO> guardar(@Valid @RequestBody ProyectoDTO proyectoDTO) {
        return new ResponseEntity<>(proyectoService.guardar(proyectoDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO> buscar(@PathVariable Long id) {
        return new ResponseEntity<>(proyectoService.buscar(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProyectoDTO> editar(@Valid @RequestBody ProyectoDTO proyectoDTO) {
        return new ResponseEntity<>(proyectoService.editar(proyectoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        proyectoService.borrar(id);
        return new ResponseEntity<>("Eliminado", HttpStatus.OK);
    }
}

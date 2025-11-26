package com.example.backend.controller;

import com.example.backend.dto.HabitoDTO;
import com.example.backend.service.HabitoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habitos")
public class HabitoController {

    @Autowired
    private HabitoService habitoService;

    @GetMapping
    public ResponseEntity<List<HabitoDTO>> listar() {
        return new ResponseEntity<>(habitoService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HabitoDTO> guardar(@Valid @RequestBody HabitoDTO habitoDTO) {
        return new ResponseEntity<>(habitoService.guardar(habitoDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitoDTO> buscar(@PathVariable Long id) {
        return new ResponseEntity<>(habitoService.buscar(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HabitoDTO> editar(@Valid @RequestBody HabitoDTO habitoDTO) {
        return new ResponseEntity<>(habitoService.editar(habitoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        habitoService.borrar(id);
        return new ResponseEntity<>("Eliminado", HttpStatus.OK);
    }
}

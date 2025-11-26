package com.example.backend.controller;

import com.example.backend.dto.NotaDTO;
import com.example.backend.service.NotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @GetMapping
    public ResponseEntity<List<NotaDTO>> listar() {
        return new ResponseEntity<>(notaService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NotaDTO> guardar(@Valid @RequestBody NotaDTO notaDTO) {
        return new ResponseEntity<>(notaService.guardar(notaDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaDTO> buscar(@PathVariable Long id) {
        return new ResponseEntity<>(notaService.buscar(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<NotaDTO> editar(@Valid @RequestBody NotaDTO notaDTO) {
        return new ResponseEntity<>(notaService.editar(notaDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        notaService.borrar(id);
        return new ResponseEntity<>("Eliminado", HttpStatus.OK);
    }
}

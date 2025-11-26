package com.example.backend.controller;

import com.example.backend.dto.MetaDTO;
import com.example.backend.service.MetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metas")
public class MetaController {

    @Autowired
    private MetaService metaService;

    @GetMapping
    public ResponseEntity<List<MetaDTO>> listar() {
        return new ResponseEntity<>(metaService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MetaDTO> guardar(@Valid @RequestBody MetaDTO metaDTO) {
        return new ResponseEntity<>(metaService.guardar(metaDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetaDTO> buscar(@PathVariable Long id) {
        return new ResponseEntity<>(metaService.buscar(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<MetaDTO> editar(@Valid @RequestBody MetaDTO metaDTO) {
        return new ResponseEntity<>(metaService.editar(metaDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        metaService.borrar(id);
        return new ResponseEntity<>("Eliminado", HttpStatus.OK);
    }
}

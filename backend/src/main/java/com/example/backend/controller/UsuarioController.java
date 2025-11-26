package com.example.backend.controller;

import com.example.backend.dto.UsuarioDTO;
import com.example.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> guardar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(usuarioService.guardar(usuarioDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.buscar(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> editar(@Valid @RequestBody UsuarioDTO usuarioDTO) {

        return new ResponseEntity<>(usuarioService.editar(usuarioDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        usuarioService.borrar(id);
        return new ResponseEntity<>("Eliminado", HttpStatus.OK);
    }
}

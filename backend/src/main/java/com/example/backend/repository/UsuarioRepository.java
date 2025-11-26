package com.example.backend.repository;

import com.example.backend.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
}
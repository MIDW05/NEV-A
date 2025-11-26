package com.example.backend.repository;

import com.example.backend.entity.ProyectoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoRepository extends JpaRepository<ProyectoEntity,Long> {
}

package com.example.backend.repository;

import com.example.backend.entity.HabitoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitoRepository extends JpaRepository<HabitoEntity, Long> {
}

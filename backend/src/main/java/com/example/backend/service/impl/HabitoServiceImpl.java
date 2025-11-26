package com.example.backend.service.impl;

import com.example.backend.dto.HabitoDTO;
import com.example.backend.entity.HabitoEntity;
import com.example.backend.mapper.HabitoMapper;
import com.example.backend.repository.HabitoRepository;
import com.example.backend.service.HabitoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HabitoServiceImpl implements HabitoService {

    @Autowired
    private HabitoRepository habitoRepository;

    @Autowired
    private HabitoMapper habitoMapper;

    @Override
    public List<HabitoDTO> listar() {
        List<HabitoEntity> habitos = habitoRepository.findAll();
        List<HabitoDTO> habitoDTOs = new ArrayList<>();
        for (HabitoEntity habitoEntity : habitos) {
            HabitoDTO habitoDTO = habitoMapper.habitoEntityAHabitoDTO(habitoEntity);
            habitoDTOs.add(habitoDTO);
        }
        return habitoDTOs;
    }

    @Override
    @Transactional
    public HabitoDTO guardar(HabitoDTO habitoDTO) {
        HabitoEntity habitoEntity = habitoMapper.habitoDTOAHabitoEntity(habitoDTO);
        return habitoMapper.habitoEntityAHabitoDTO(habitoRepository.save(habitoEntity));
    }

    @Override
    public HabitoDTO buscar(Long id) {
        HabitoEntity habitoEntity = habitoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hábito no encontrado"));
        return habitoMapper.habitoEntityAHabitoDTO(habitoEntity);
    }

    @Override
    public HabitoDTO editar(HabitoDTO habitoDTO) {
        HabitoEntity habitoEntity = habitoRepository.findById(habitoDTO.getId()).get();
        habitoEntity.setNombre(habitoDTO.getNombre());
        HabitoDTO habitoDTO1 = habitoMapper.habitoEntityAHabitoDTO(habitoRepository.save(habitoEntity));
        return habitoDTO1;
    }

    @Override
    public void borrar(Long id) {
        habitoRepository.deleteById(id);
    }
}

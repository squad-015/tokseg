package com.squad15.armariointeligente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad15.armariointeligente.exception.ResourceNotFoundException;
import com.squad15.armariointeligente.model.Retirada;
import com.squad15.armariointeligente.repository.RetiradaRepository;

import jakarta.transaction.Transactional;

@Service
public class RetiradaService {

    @Autowired
    private RetiradaRepository retiradaRepository;

    @Transactional
    public List<Retirada> listarRetiradas() {
        return retiradaRepository.findAll();
    }

    @Transactional
    public Retirada buscarRetiradaPorId(Long id) {
        return retiradaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Retirada", "id", id));
    }
}

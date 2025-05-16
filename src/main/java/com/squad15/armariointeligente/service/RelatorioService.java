package com.squad15.armariointeligente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad15.armariointeligente.exception.ResourceNotFoundException;
import com.squad15.armariointeligente.model.Relatorio;
import com.squad15.armariointeligente.repository.RelatorioRepository;

import jakarta.transaction.Transactional;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Transactional
    public List<Relatorio> listarRelatorios() {
        return relatorioRepository.findAll();
    }

    @Transactional
    public Relatorio buscarRelatorioPorId(Long id) {
        return relatorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relatório", "id", id));
    }
}

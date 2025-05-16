package com.squad15.armariointeligente.controller;

import com.squad15.armariointeligente.model.Relatorio;
import com.squad15.armariointeligente.service.RelatorioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    public ResponseEntity<List<Relatorio>> listarRelatorios() {
        List<Relatorio> relatorios = relatorioService.listarRelatorios();
        return ResponseEntity.ok(relatorios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Relatorio> buscarPorId(@PathVariable Long id) {
        Relatorio relatorio = relatorioService.buscarRelatorioPorId(id);
        return ResponseEntity.ok(relatorio);
    }
}

package com.squad15.armariointeligente.controller;

import com.squad15.armariointeligente.model.Retirada;
import com.squad15.armariointeligente.service.RetiradaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/retiradas")
public class RetiradaController {

    @Autowired
    private RetiradaService retiradaService;

    @GetMapping
    public ResponseEntity<List<Retirada>> listarRetiradas() {
        List<Retirada> retiradas = retiradaService.listarRetiradas();
        return ResponseEntity.ok(retiradas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Retirada> buscarPorId(@PathVariable Long id) {
        Retirada retirada = retiradaService.buscarRetiradaPorId(id);
        return ResponseEntity.ok(retirada);
    }
}

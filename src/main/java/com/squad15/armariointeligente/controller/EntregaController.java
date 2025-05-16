package com.squad15.armariointeligente.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad15.armariointeligente.exception.ResourceNotFoundException;
import com.squad15.armariointeligente.model.Entrega;
import com.squad15.armariointeligente.service.EntregaService;

@RestController
@RequestMapping("/api/v1/entregas")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @PostMapping
    public ResponseEntity<Entrega> criarEntrega(@RequestBody Entrega entrega) {
        Entrega novaEntrega = entregaService.cadastrarEntrega(entrega);
        return ResponseEntity.ok(novaEntrega);
    }

    @GetMapping
    public ResponseEntity<List<Entrega>> listarEntregas() {
        return ResponseEntity.ok(entregaService.listarEntregas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrega> buscarEntregaPorId(@PathVariable Long id) {
        Optional<Entrega> entrega = entregaService.buscarEntregaPorId(id);
        if (entrega.isPresent()) {
            return ResponseEntity.ok(entrega.get());
        } else {
            throw new ResourceNotFoundException("Entrega", "id", id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrega> atualizarEntrega(@PathVariable Long id, @RequestBody Entrega entrega) {
        Entrega entregaAtualizada = entregaService.atualizarEntrega(id, entrega);
        return ResponseEntity.ok(entregaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEntrega(@PathVariable Long id) {
        entregaService.deletarEntrega(id);
        return ResponseEntity.noContent().build();
    }
    
}

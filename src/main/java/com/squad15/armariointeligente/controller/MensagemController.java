package com.squad15.armariointeligente.controller;

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
import com.squad15.armariointeligente.model.Mensagem;
import com.squad15.armariointeligente.service.MensagemService;

@RestController
@RequestMapping("/api/v1/mensagens")
public class MensagemController {

    @Autowired
    private MensagemService mensagemService;

    @PostMapping
    public ResponseEntity<?> criarMensagem(@RequestBody Mensagem mensagem) {
        Mensagem novaMensagem = mensagemService.criarMensagem(mensagem);
        return ResponseEntity.ok(novaMensagem);
    }

    @GetMapping
    public ResponseEntity<?> listarMensagens() {
        return ResponseEntity.ok(mensagemService.listarMensagens());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMensagemPorId(@PathVariable Long id) {
        Optional<Mensagem> mensagem = mensagemService.buscarMensagemPorId(id);
        if (mensagem.isPresent()) {
            return ResponseEntity.ok(mensagem.get());
        } else {
            throw new ResourceNotFoundException("Mensagem", "id", id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarMensagem(@PathVariable Long id, @RequestBody Mensagem mensagem) {
        Mensagem mensagemAtualizada = mensagemService.atualizarMensagem(id, mensagem);
        return ResponseEntity.ok(mensagemAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarMensagem(@PathVariable Long id) {
        mensagemService.deletarMensagem(id);
        return ResponseEntity.noContent().build();
    }
    
}

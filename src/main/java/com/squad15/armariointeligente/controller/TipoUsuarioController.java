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
import com.squad15.armariointeligente.model.TipoUsuario;
import com.squad15.armariointeligente.service.TipoUsuarioService;

@RestController
@RequestMapping("/api/v1/tipos-usuarios")
public class TipoUsuarioController {
	
	@Autowired
    private TipoUsuarioService tipoUsuarioService;

    @PostMapping
    public ResponseEntity<?> criarTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario novoTipoUsuario = tipoUsuarioService.criarTipoUsuario(tipoUsuario);
        return ResponseEntity.ok(novoTipoUsuario);
    }

    @GetMapping
    public ResponseEntity<?> listarTiposUsuarios() {
        return ResponseEntity.ok(tipoUsuarioService.listarTiposUsuarios());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTipoUsuarioPorId(@PathVariable Long id) {
        Optional<TipoUsuario> tipoUsuario = tipoUsuarioService.buscarTipoUsuarioPorId(id);
        if (tipoUsuario.isPresent()) {
            return ResponseEntity.ok(tipoUsuario.get());
        } else {
            throw new ResourceNotFoundException("Tipo de usuário", "id", id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarTipoUsuario(@PathVariable Long id,
            @RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario tipoAtualizado = tipoUsuarioService.atualizarTipoUsuario(id, tipoUsuario);
        return ResponseEntity.ok(tipoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarTipoUsuario(@PathVariable Long id) {
        tipoUsuarioService.deletarTipoUsuario(id);
        return ResponseEntity.noContent().build();
    }

}

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
import com.squad15.armariointeligente.model.Armario;
import com.squad15.armariointeligente.service.ArmarioService;

@RestController
@RequestMapping("/api/v1/armarios")
public class ArmarioController {
	
	@Autowired
	private ArmarioService armarioService;
	
	@PostMapping
	public ResponseEntity<?> criarArmario(@RequestBody Armario armario) {
		Armario novoArmario = armarioService.criarArmario(armario);
		return ResponseEntity.ok(novoArmario);
	}
	
	@GetMapping
	public ResponseEntity<?> listarArmarios() {
		return ResponseEntity.ok(armarioService.listarArmarios());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarArmarioPorId(@PathVariable Long id) {
		Optional<Armario> armario = armarioService.buscarArmarioPorId(id);
		if (armario.isPresent()) {
			return ResponseEntity.ok(armario.get());
		} else {
			throw new ResourceNotFoundException("Armário", "id", id);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarArmario(@PathVariable Long id, @RequestBody Armario armario) {
		Armario armarioAtualizado = armarioService.atualizarArmario(id, armario);
		return ResponseEntity.ok(armarioAtualizado);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarArmario(@PathVariable Long id) {
		armarioService.deletarArmario(id);
		return ResponseEntity.noContent().build();
	}

}

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
import com.squad15.armariointeligente.model.Compartimento;
import com.squad15.armariointeligente.service.CompartimentoService;

@RestController
@RequestMapping("/api/v1/compartimentos")
public class CompartimentoController {
	
	@Autowired
	private CompartimentoService compartimentoService;
	
	@PostMapping
	public ResponseEntity<?> criarCompartimento(@RequestBody Compartimento compartimento) {
		Compartimento novoCompartimento = compartimentoService.criarCompartimento(compartimento);
		return ResponseEntity.ok(novoCompartimento);
	}
	
	@GetMapping
	public ResponseEntity<?> listarCompartimentos() {
		return ResponseEntity.ok(compartimentoService.listarCompartimentos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarCompartimentoPorId(@PathVariable Long id) {
		Optional<Compartimento> compartimento = compartimentoService.buscarCompartimentoPorId(id);
		if (compartimento.isPresent()) {
			return ResponseEntity.ok(compartimento.get());
		} else {
			throw new ResourceNotFoundException("Compartimento", "id", id);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarCompartimento(@PathVariable Long id, @RequestBody Compartimento compartimento) {
		Compartimento compartimentoAtualizado = compartimentoService.atualizarCompartimento(id, compartimento);
		return ResponseEntity.ok(compartimentoAtualizado);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarCompartimento(@PathVariable Long id) {
		compartimentoService.deletarCompartimento(id);
		return ResponseEntity.noContent().build();
	}

}

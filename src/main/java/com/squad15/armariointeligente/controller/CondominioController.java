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
import com.squad15.armariointeligente.model.Condominio;
import com.squad15.armariointeligente.service.CondominioService;

@RestController
@RequestMapping("/api/v1/condominios")
public class CondominioController {
	
	@Autowired
	private CondominioService condominioService;
	
	@PostMapping
	public ResponseEntity<?> criarCondominio(@RequestBody Condominio condominio) {
		Condominio novoCondominio = condominioService.criarCondominio(condominio);
		return ResponseEntity.ok(novoCondominio);
	}
	
	@GetMapping
	public ResponseEntity<?> listarCondominios() {
		return ResponseEntity.ok(condominioService.listarCondominios());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarCondominioPorId(@PathVariable Long id) {
		Optional<Condominio> condominio = condominioService.buscarCondominioPorId(id);
		if (condominio.isPresent()) {
			return ResponseEntity.ok(condominio.get());
		} else {
			throw new ResourceNotFoundException("Condominio", "id", id);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarCondominio(@PathVariable Long id,
			@RequestBody Condominio condominio) {
		Condominio condominioAtualizado = condominioService.atualizarCondominio(id, condominio);
		return ResponseEntity.ok(condominioAtualizado);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarCondominio(@PathVariable Long id) {
		condominioService.deletarCondominio(id);
		return ResponseEntity.noContent().build();
	}
	
}

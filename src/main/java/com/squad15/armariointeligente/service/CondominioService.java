package com.squad15.armariointeligente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad15.armariointeligente.exception.BadRequestException;
import com.squad15.armariointeligente.exception.RelatedResourceException;
import com.squad15.armariointeligente.exception.ResourceAlreadyExistsException;
import com.squad15.armariointeligente.exception.ResourceNotFoundException;
import com.squad15.armariointeligente.model.Condominio;
import com.squad15.armariointeligente.repository.CondominioRepository;

import jakarta.transaction.Transactional;

@Service
public class CondominioService {
	
	@Autowired
	private CondominioRepository condominioRepository;
	
	@Transactional
	public Condominio criarCondominio(Condominio condominio) {
		if (condominio == null) {
			throw new BadRequestException("O condomínio não pode ser nulo");
		}
		if (condominio.getNome() == null || condominio.getNome().isEmpty()) {
			throw new BadRequestException("O nome do condomínio não pode ser nulo ou vazio");
		}
		condominioRepository.findByNome(condominio.getNome()).ifPresent(c -> {
			throw new ResourceAlreadyExistsException("Condomínio", "nome", condominio.getNome());
		});
		
		return condominioRepository.save(condominio);
	}
	
	@Transactional
	public List<Condominio> listarCondominios() {
		return condominioRepository.findAll();
	}
	
	@Transactional
	public Optional<Condominio> buscarCondominioPorId(Long id) {
		return condominioRepository.findById(id);
	}
	
	@Transactional
	public Condominio atualizarCondominio(Long id, Condominio condominio) {
		Condominio condominioExistente = condominioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Condomínio", "id", id));
		
		if (condominio.getNome() != null && !condominio.getNome().isEmpty()) {
			Optional<Condominio> condominioComMesmoNome = condominioRepository.findByNome(condominio.getNome());
			if (condominioComMesmoNome.isPresent() && !condominioComMesmoNome.get().getId().equals(id)) {
				throw new ResourceAlreadyExistsException("Condomínio", "nome", condominio.getNome());
			}
			condominioExistente.setNome(condominio.getNome());
		}
		
		return condominioRepository.save(condominioExistente);
	}
	
	@Transactional
	public void deletarCondominio(Long id) {
		Condominio condominioExistente = condominioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Condomínio", "id", id));
		
		if (!condominioExistente.getArmarios().isEmpty()) {
			throw new RelatedResourceException("condomínio", "armarios");
		}
		
		condominioRepository.deleteById(id);
	}
}

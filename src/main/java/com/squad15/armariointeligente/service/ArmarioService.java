package com.squad15.armariointeligente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad15.armariointeligente.exception.BadRequestException;
import com.squad15.armariointeligente.exception.ResourceNotFoundException;
import com.squad15.armariointeligente.model.Armario;
import com.squad15.armariointeligente.model.Condominio;
import com.squad15.armariointeligente.repository.ArmarioRepository;
import com.squad15.armariointeligente.repository.CondominioRepository;

import jakarta.transaction.Transactional;

@Service
public class ArmarioService {
	
	@Autowired
	private ArmarioRepository armarioRepository;
	
	@Autowired
	private CondominioRepository condominioRepository;
	
	@Transactional
	public Armario criarArmario(Armario armario) {
		if (armario == null) {
			throw new BadRequestException("Armário não pode ser nulo");
		}
		if (armario.getLocalizacao() == null || armario.getLocalizacao().isEmpty()) {
			throw new BadRequestException("Localização não pode ser nula ou vazia");
		}
		if (armario.getCondominio() == null || armario.getCondominio().getId() == null) {
			throw new BadRequestException("Condomínio é obrigatório");
		}
		
		Condominio condominio = condominioRepository.findById(armario.getCondominio().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Condomínio", "id", armario.getCondominio().getId()));
		
		armario.setCondominio(condominio);
		return armarioRepository.save(armario);
	}
	
	@Transactional
	public List<Armario> listarArmarios() {
		return armarioRepository.findAll();
	}
	
	@Transactional
	public Optional<Armario> buscarArmarioPorId(Long id) {
		return armarioRepository.findById(id);
	}
	
	@Transactional
	public Armario atualizarArmario(Long id, Armario armarioAtualizado) {
		Armario armarioExistente = armarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Armário", "id", id));
		
		if (armarioAtualizado == null) {
			throw new BadRequestException("Armário não pode ser nulo");
		}
		if (armarioAtualizado.getLocalizacao() == null || armarioAtualizado.getLocalizacao().isEmpty()) {
			throw new BadRequestException("Localização não pode ser nula ou vazia");
		}
		
		if (!armarioExistente.getCondominio().getId().equals(armarioAtualizado.getCondominio().getId())) {
			throw new BadRequestException("Não é possível alterar o condomínio do armário");
		}
		
		return armarioRepository.save(armarioAtualizado);
	}
	
	@Transactional
	public void deletarArmario(Long id) {
		if (!armarioRepository.existsById(id)) {
			throw new ResourceNotFoundException("Armário", "id", id);
		}
		armarioRepository.deleteById(id);
	}

}

package com.squad15.armariointeligente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad15.armariointeligente.exception.BadRequestException;
import com.squad15.armariointeligente.exception.ResourceAlreadyExistsException;
import com.squad15.armariointeligente.exception.ResourceNotFoundException;
import com.squad15.armariointeligente.model.Armario;
import com.squad15.armariointeligente.model.Compartimento;
import com.squad15.armariointeligente.repository.ArmarioRepository;
import com.squad15.armariointeligente.repository.CompartimentoRepository;

import jakarta.transaction.Transactional;

@Service
public class CompartimentoService {
	
	@Autowired
	private CompartimentoRepository compartimentoRepository;
	
	@Autowired
	private ArmarioRepository armarioRepository;
	
	@Transactional
	public Compartimento criarCompartimento(Compartimento compartimento) {
		if (compartimento == null) {
			throw new BadRequestException("Compartimento não pode ser nulo");
		}
		if (compartimento.getNumero() == null || compartimento.getNumero() <= 0) {
			throw new BadRequestException("Número do compartimento não pode ser nulo");
		}
		if (compartimento.getArmario() == null || compartimento.getArmario().getId() == null) {
			throw new BadRequestException("Armário é obrigatório");
		}
		Optional<Compartimento> duplicado = compartimentoRepository
			.findByNumeroAndArmarioId(compartimento.getNumero(), compartimento.getArmario().getId());
		if (duplicado.isPresent()) {
			throw new ResourceAlreadyExistsException("Compartimento", "número", compartimento.getNumero());
		}
		
		Armario armario = armarioRepository.findById(compartimento.getArmario().getId())
				.orElseThrow(() -> new ResourceNotFoundException("Armário", "id", compartimento.getArmario().getId()));
		
		compartimento.setArmario(armario);
		return compartimentoRepository.save(compartimento);
	}
	
	@Transactional
	public List<Compartimento> listarCompartimentos() {
		return compartimentoRepository.findAll();
	}
	
	@Transactional
	public Optional<Compartimento> buscarCompartimentoPorId(Long id) {
		return compartimentoRepository.findById(id);
	}
	
	@Transactional
	public Compartimento atualizarCompartimento(Long id, Compartimento compartimento) {
		Compartimento compartimentoExistente = compartimentoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Compartimento", "id", id));
		
		if (compartimento.getNumero() != null && compartimento.getNumero() > 0) {
			compartimentoExistente.setNumero(compartimento.getNumero());
		}
		
		if (compartimento.getArmario() == null || compartimento.getArmario().getId() == null) {
			throw new BadRequestException("Armário é obrigatório");
		}
		
		if (compartimento.getArmario() != null && compartimento.getArmario().getId() != null) {
			Armario armario = armarioRepository.findById(compartimento.getArmario().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Armário", "id", compartimento.getArmario().getId()));
			compartimentoExistente.setArmario(armario);
		}
		
		if (compartimento.getStatus() != null && !compartimento.getStatus().isEmpty()) {
			compartimentoExistente.setStatus(compartimento.getStatus());
		}
		
		if (compartimentoRepository.findByArmarioId(compartimento.getId()).isPresent()) {
			throw new ResourceAlreadyExistsException("Compartimento", "número", compartimento.getNumero());
		}
		
		
		return compartimentoRepository.save(compartimentoExistente);
	}
	
	@Transactional
	public void deletarCompartimento(Long id) {
		if (!compartimentoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Compartimento", "id", id);
		}
		compartimentoRepository.deleteById(id);
	}

}

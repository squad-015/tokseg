package com.squad15.armariointeligente.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad15.armariointeligente.exception.BadRequestException;
import com.squad15.armariointeligente.exception.ResourceNotFoundException;
import com.squad15.armariointeligente.model.Compartimento;
import com.squad15.armariointeligente.model.Entrega;
import com.squad15.armariointeligente.model.Usuario;
import com.squad15.armariointeligente.repository.CompartimentoRepository;
import com.squad15.armariointeligente.repository.EntregaRepository;
import com.squad15.armariointeligente.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CompartimentoRepository compartimentoRepository;

    @Transactional
    public Entrega cadastrarEntrega(Entrega entrega) {
        if (entrega == null || entrega.getCodigoRastreio() == null || entrega.getCodigoRastreio().isEmpty()) {
            throw new BadRequestException("Código de rastreio é obrigatório");
        }

        if (entrega.getUsuario() == null || entrega.getUsuario().getId() == null) {
            throw new BadRequestException("Usuário não encontrado");
        }

        if (entrega.getCompartimento() == null || entrega.getCompartimento().getId() == null) {
            throw new BadRequestException("Compartimento é obrigatório");
        }

        Usuario usuario = usuarioRepository.findById(entrega.getUsuario().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", entrega.getUsuario().getId()));

        Compartimento compartimento = compartimentoRepository.findById(entrega.getCompartimento().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Compartimento", "id", entrega.getCompartimento().getId()));

        entrega.setUsuario(usuario);
        entrega.setCompartimento(compartimento);
        entrega.setDataEntrega(LocalDateTime.now());

        return entregaRepository.save(entrega);
    }

    @Transactional
    public List<Entrega> listarEntregas() {
        return entregaRepository.findAll();
    }

    @Transactional
    public Optional<Entrega> buscarEntregaPorId(Long id) {
        return entregaRepository.findById(id);
    }

    @Transactional
    public Entrega atualizarEntrega(Long id, Entrega entregaAtualizada) {
        Entrega existente = entregaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrega", "id", id));

        if (entregaAtualizada.getCodigoRastreio() != null && !entregaAtualizada.getCodigoRastreio().isEmpty()
                && !entregaAtualizada.getCodigoRastreio().equals(existente.getCodigoRastreio())) {
            if (entregaRepository.existsByCodigoRastreio(entregaAtualizada.getCodigoRastreio())) {
                throw new ResourceAlreadyExistsException("Entrega", "código de rastreio", entregaAtualizada.getCodigoRastreio());
            }
            existente.setCodigoRastreio(entregaAtualizada.getCodigoRastreio());
        }

        if (entregaAtualizada.getUsuario() != null && entregaAtualizada.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(entregaAtualizada.getUsuario().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", entregaAtualizada.getUsuario().getId()));
            existente.setUsuario(usuario);
        }

        if (entregaAtualizada.getCompartimento() != null && entregaAtualizada.getCompartimento().getId() != null) {
            Compartimento compartimento = compartimentoRepository.findById(entregaAtualizada.getCompartimento().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Compartimento", "id", entregaAtualizada.getCompartimento().getId()));
            existente.setCompartimento(compartimento);
        }

        return entregaRepository.save(existente);
    }

    @Transactional
    public void deletarEntrega(Long id) {
        if (!entregaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Entrega", "id", id);
        }
        entregaRepository.deleteById(id);
    }
}

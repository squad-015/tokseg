package com.squad15.armariointeligente.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad15.armariointeligente.exception.BadRequestException;
import com.squad15.armariointeligente.exception.ResourceNotFoundException;
import com.squad15.armariointeligente.model.Entrega;
import com.squad15.armariointeligente.model.Mensagem;
import com.squad15.armariointeligente.model.Usuario;
import com.squad15.armariointeligente.repository.EntregaRepository;
import com.squad15.armariointeligente.repository.MensagemRepository;
import com.squad15.armariointeligente.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntregaRepository entregaRepository;

    @Transactional
    public Mensagem criarMensagem(Mensagem mensagem) {
        if (mensagem == null || mensagem.getConteudo() == null || mensagem.getConteudo().isEmpty()) {
            throw new BadRequestException("Conteúdo da mensagem é obrigatório");
        }

        if (mensagem.getUsuario() == null || mensagem.getUsuario().getId() == null) {
            throw new BadRequestException("Usuário é obrigatório");
        }

        if (mensagem.getEntrega() == null || mensagem.getEntrega().getId() == null) {
            throw new BadRequestException("Entrega é obrigatória");
        }

        Usuario usuario = usuarioRepository.findById(mensagem.getUsuario().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", mensagem.getUsuario().getId()));

        Entrega entrega = entregaRepository.findById(mensagem.getEntrega().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Entrega", "id", mensagem.getEntrega().getId()));

        mensagem.setUsuario(usuario);
        mensagem.setEntrega(entrega);
        mensagem.setDataEnvio(LocalDateTime.now());

        return mensagemRepository.save(mensagem);
    }

    @Transactional
    public List<Mensagem> listarMensagens() {
        return mensagemRepository.findAll();
    }

    @Transactional
    public Optional<Mensagem> buscarMensagemPorId(Long id) {
        return mensagemRepository.findById(id);
    }

    @Transactional
    public Mensagem atualizarMensagem(Long id, Mensagem atualizada) {
        Mensagem existente = mensagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mensagem", "id", id));

        if (atualizada.getConteudo() != null && !atualizada.getConteudo().isEmpty()) {
            existente.setConteudo(atualizada.getConteudo());
        }

        if (atualizada.getUsuario() != null && atualizada.getUsuario().getId() != null) {
            Usuario usuario = usuarioRepository.findById(atualizada.getUsuario().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário", "id", atualizada.getUsuario().getId()));
            existente.setUsuario(usuario);
        }

        if (atualizada.getEntrega() != null && atualizada.getEntrega().getId() != null) {
            Entrega entrega = entregaRepository.findById(atualizada.getEntrega().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Entrega", "id", atualizada.getEntrega().getId()));
            existente.setEntrega(entrega);
        }

        return mensagemRepository.save(existente);
    }

    @Transactional
    public void deletarMensagem(Long id) {
        if (!mensagemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mensagem", "id", id);
        }
        mensagemRepository.deleteById(id);
    }
    
}

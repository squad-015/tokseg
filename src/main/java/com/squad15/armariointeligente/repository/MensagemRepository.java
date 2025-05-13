package com.squad15.armariointeligente.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.squad15.armariointeligente.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    
    List<Mensagem> findByUsuarioId(Long usuarioId);

    List<Mensagem> findByEntregaId(Long entregaId);
    
}

package com.squad15.armariointeligente.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.squad15.armariointeligente.model.Retirada;

public interface RetiradaRepository extends JpaRepository<Retirada, Long> {

    List<Retirada> findByUsuarioId(Long usuarioId);
    
    List<Retirada> findByEntregaId(Long entregaId);
}

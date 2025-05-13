package com.squad15.armariointeligente.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.squad15.armariointeligente.model.Entrega;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    
    List<Entrega> findByUsuarioId(Long usuarioId);
    
    List<Entrega> findByCompartimentoId(Long compartimentoId);

    boolean existsCodigoRastreio(String codigoRastreio);
    
}

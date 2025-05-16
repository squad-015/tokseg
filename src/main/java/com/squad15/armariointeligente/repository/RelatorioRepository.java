package com.squad15.armariointeligente.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.squad15.armariointeligente.model.Relatorio;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {

    List<Relatorio> findByUsuarioId(Long usuarioId);
}

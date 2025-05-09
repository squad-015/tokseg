package com.squad15.armariointeligente.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.squad15.armariointeligente.model.Armario;

public interface ArmarioRepository extends JpaRepository<Armario, Long> {
	
	Optional<Armario> findByCondominioId(Long idCondominio);

}

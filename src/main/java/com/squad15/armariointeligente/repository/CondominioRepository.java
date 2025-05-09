package com.squad15.armariointeligente.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.squad15.armariointeligente.model.Condominio;

public interface CondominioRepository extends JpaRepository<Condominio, Long> {
	
	 Optional<Condominio> findByNome(String nome);
	

}

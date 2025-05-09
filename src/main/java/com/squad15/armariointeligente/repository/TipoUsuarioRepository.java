package com.squad15.armariointeligente.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.squad15.armariointeligente.model.TipoUsuario;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long> {
	
	Optional<TipoUsuario> findByNome(String nome);

}

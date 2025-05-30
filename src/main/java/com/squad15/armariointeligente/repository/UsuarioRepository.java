package com.squad15.armariointeligente.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.squad15.armariointeligente.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);
	boolean existsByEmail(String email);
	
}

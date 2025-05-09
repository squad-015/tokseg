package com.squad15.armariointeligente.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.squad15.armariointeligente.model.Compartimento;

public interface CompartimentoRepository extends JpaRepository<Compartimento, Long> {
	
	 Optional<Compartimento> findByArmarioId(Long armarioId);

	 Optional<Compartimento> findByNumeroAndArmarioId(Integer numero, Long armarioId);


}

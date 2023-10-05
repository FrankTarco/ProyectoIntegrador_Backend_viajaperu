package com.viajaperu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viajaperu.models.EquipoBus;

public interface EquipoBusRepository extends JpaRepository<EquipoBus, String>{

	@Query("SELECT MAX(b.cod_equipo) FROM EquipoBus b")
	public String ultimoId();
	
	
	List<EquipoBus> findByCodterramoza(String codigoTerra);
	
	@Query("SELECT b FROM EquipoBus b where b.codchofer= ?1 or b.codcopiloto= ?2")
	List<EquipoBus> buscarconductordentrodeEquipo(String chofer, String copiloto);
	

	
}

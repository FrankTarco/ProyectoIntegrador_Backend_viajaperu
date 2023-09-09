package com.viajaperu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viajaperu.models.Bus;
import java.util.List;


public interface BusRepository extends JpaRepository<Bus, String>{

	@Query("SELECT MAX(b.cod_bus) FROM Bus b")
	public String ultimoId();
	
	List<Bus> findByPlaca(String placa);
	
	@Query("SELECT b FROM Bus b where b.placa= ?1 and b.cod_bus<> ?2")
	List<Bus> buscarPorPlacaDiferenteCodigo(String placa, String codigo);
	
}

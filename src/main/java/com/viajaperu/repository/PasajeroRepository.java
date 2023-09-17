package com.viajaperu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viajaperu.models.Pasajero;

public interface PasajeroRepository extends JpaRepository<Pasajero, String>{

	public List<Pasajero> findByNumeroDocumento(String numeroDocumento);
	
	@Query("SELECT MAX(p.cod_pasajero) FROM Pasajero p")
	public String ultimoCodigoPasajero();
	
}

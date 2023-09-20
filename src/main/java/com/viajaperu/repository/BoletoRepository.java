package com.viajaperu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viajaperu.models.Boleto;

public interface BoletoRepository extends JpaRepository<Boleto, String>{

	@Query("SELECT MAX(b.cod_boleto) FROM Boleto b")
	public String ultimoCodigoBoleto();
	
}

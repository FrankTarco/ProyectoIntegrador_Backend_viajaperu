package com.viajaperu.creditcard;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Integer>{

	public List<TarjetaCredito> findByNumero(String numero);
	
}

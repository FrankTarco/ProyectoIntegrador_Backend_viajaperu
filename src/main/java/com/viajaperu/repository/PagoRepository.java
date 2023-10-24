package com.viajaperu.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viajaperu.models.Pago;

public interface PagoRepository extends JpaRepository<Pago, String>{

	@Query("SELECT MAX(p.cod_pago) FROM Pago p")
	public String ultimoCodigoPago();
	
	@Query("SELECT p FROM Pago p WHERE p.numero_tarjeta= ?1")
	public Pago buscarPagosPorNumeroTarjeta(String numero);
	
	@Query("SELECT p FROM Pago p ORDER BY p.cod_pago DESC LIMIT 1")
	public Pago ultimoPago();
}

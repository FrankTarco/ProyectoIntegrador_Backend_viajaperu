package com.viajaperu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viajaperu.models.Boleto;

public interface BoletoRepository extends JpaRepository<Boleto, String>{

	@Query("SELECT MAX(b.cod_boleto) FROM Boleto b")
	public String ultimoCodigoBoleto();
	
	@Query("select b.numero_asiento FROM Boleto b JOIN b.venta vb WHERE vb.cod_itinerario= ?1")
	public List<Integer>listarBoletosCompradosPorItinerario(String codigo);
}

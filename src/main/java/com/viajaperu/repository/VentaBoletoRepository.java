package com.viajaperu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viajaperu.models.VentaBoleto;

public interface VentaBoletoRepository extends JpaRepository<VentaBoleto, String>{

	@Query("SELECT MAX(v.cod_venta) FROM VentaBoleto v")
	public String ultimoCodVenta();
	
	@Query("SELECT v FROM VentaBoleto v WHERE v.cod_venta= ?1")
	public VentaBoleto encontrarVenta(String codigo);
	
	@Query("SELECT CONCAT(o.nombre, ' - ', ll.nombre) AS viaje, " +
	           "SUM(v.precioTotal) AS sumaOperaciones " +
	           "FROM VentaBoleto v " +
	           "JOIN Itinerario i ON i.cod_itinerario = v.itinerario.cod_itinerario " +
	           "JOIN Destino o ON i.codorigen = o.cod_destino " +
	           "JOIN Destino ll ON i.codllegada = ll.cod_destino " +
	           "GROUP BY viaje " +
	           "ORDER BY sumaOperaciones DESC " +
	           "LIMIT 4")
	public List<Object[]> listarParaGrafico();
	
}

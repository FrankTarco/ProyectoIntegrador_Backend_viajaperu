package com.viajaperu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viajaperu.models.Itinerario;

public interface ItinerarioRepository extends JpaRepository<Itinerario, String>{
	
	@Query("SELECT MAX(i.cod_itinerario) FROM Itinerario i")
	public String ultimoId();
	
	List<Itinerario> findByCodequipo(String codequipo);
	
	List<Itinerario> findByCodbus(String codbus);
	
	@Query("SELECT i FROM Itinerario i where i.codorigen= ?1 or i.codllegada= ?2")
	List<Itinerario> buscarDestino(String codorigen, String codllegada);
	
	@Query("select i from Itinerario i JOIN Destino origen ON i.codorigen=origen.cod_destino JOIN Destino llegada ON i.codllegada=llegada.cod_destino WHERE origen.nombre= ?1 and llegada.nombre= ?2 and i.fecha_salida>= ?3")
	public List<Itinerario>listarItinerariosPorNombresyFecha(String origen,String llegada,String fecha);
	
	@Query("select i FROM Itinerario i WHERE i.codbus= ?1")
	public List<Itinerario>buscarItinerariosPorCodigoBus(String codbus);
	
	@Query("select i FROM Itinerario i WHERE i.codequipo= ?1")
	public List<Itinerario>buscarItinerarioPorCodigoEquipo(String codequipo);
	
}

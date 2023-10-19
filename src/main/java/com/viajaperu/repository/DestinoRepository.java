package com.viajaperu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viajaperu.models.Destino;

public interface DestinoRepository extends JpaRepository<Destino, String>{

	public List<Destino> findByNombre(String nombre);
	
	@Query("select d from Destino d where d.nombre= ?1 and d.sucursal= ?2 and d.cod_destino<> ?3")
	public List<Destino>buscarDestinoDiferenteDelMismo(String nombre,String sucursal, String codigo);
	
	@Query("SELECT MAX(d.cod_destino) FROM Destino d")
	public String ultimoId();
	
	public List<Destino>findByNombreAndSucursal(String nombre,String sucursal);
	
	@Query("select d from Destino d where d.nombre<> ?1")
	public List<Destino>destinoDiferenteDeOtro(String nombreDestino);
	
	@Query("select distinct d.nombre from Destino d")
	public List<String>listarDestinosDiferentes();
	
	@Query("select distinct d.nombre from Destino d where d.nombre<> ?1")
	public List<String>listarDestinosDiferentesSiMismo(String origen);
	
	@Query("SELECT d FROM Destino d WHERE cod_destino= ?1 AND EXISTS (SELECT 1 FROM Itinerario i WHERE i.codorigen = d.cod_destino or i.codllegada = d.cod_destino)")
	public List<Destino>existeDestinoEnItinerario(String codigo);
	
}

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
}
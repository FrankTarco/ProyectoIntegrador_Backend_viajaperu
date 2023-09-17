package com.viajaperu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viajaperu.models.Cliente;
import java.util.List;


public interface ClienteRepository extends JpaRepository<Cliente, String>{

	@Query("SELECT MAX(c.cod_cliente) FROM Cliente c")
	public String ultimoCodigoString();
	
	public List<Cliente> findByNumeroDocumento(String numeroDocumento);
}

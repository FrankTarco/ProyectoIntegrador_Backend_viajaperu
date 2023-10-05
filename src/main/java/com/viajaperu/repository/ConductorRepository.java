package com.viajaperu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.viajaperu.models.Conductor;

public interface ConductorRepository extends JpaRepository<Conductor, String> {

	@Query("SELECT MAX(b.cod_conductor) FROM Conductor b")
	public String ultimoId();
	
	List<Conductor> findByNrolicencia(String licencia);
	
	List<Conductor> findByNrodocumento(String documento);
	
	@Query("select b from Conductor b where b.nrolicencia=?1 and b.cod_conductor<>?2")
	List<Conductor> buscarPorLicenciaDiferenteCodigo(String licencia, String codigo);
	
	@Query("select b from Conductor b where b.nrodocumento=?1 and b.cod_conductor<>?2")
	List<Conductor> buscarPorDocumentoDiferenteCodigo(String nrodocumento, String codigo);

	@Query("SELECT c FROM Conductor c WHERE NOT EXISTS (SELECT 1 FROM EquipoBus e WHERE e.codchofer = c.cod_conductor or e.codcopiloto = c.cod_conductor)")
	List<Conductor>disponiblesSeleccion();
	
	@Query("SELECT c FROM Conductor c WHERE cod_conductor<>?1 AND NOT EXISTS (SELECT 1 FROM EquipoBus e WHERE e.codchofer = c.cod_conductor or e.codcopiloto = c.cod_conductor)")
	List<Conductor>buscarCopilotoParaEquipoBus(String cod_conductor);
}

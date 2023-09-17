package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import com.viajaperu.models.Terramoza;


public interface TerramozaService {
		
	public List<Terramoza> listaTerramoza();
	public Terramoza insertaTerramoza(Terramoza objTerramoza);
	public Terramoza actualizaTerramoza(Terramoza objTerramoza);
	public void eliminaTerramoza(String cod_terramoza);
	public Optional<Terramoza> buscaTerramoza(String cod_terramoza);
	public String ultimoRegistroString();
	public List<Terramoza> buscarTerramozaPorDni(String numero_documento);
	public List<Terramoza> buscarTerramozaPorDniyCodigo(String numero_documento,String cod_terramoza);
	
}

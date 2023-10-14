package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import com.viajaperu.models.Conductor;

public interface ConductorService {
	
	public List<Conductor> listarConductor();
	public Conductor registrarActualizarConductor(Conductor conductor);
	public void eliminarConductor(String codigo);
	public String ultimoIdString();
	public Optional<Conductor> buscarPorId(String codigo);
	public List<Conductor>listarConductorXLicencia(String licencia);
	public List<Conductor>listarConductorXDocumento(String documento);
	public List<Conductor>listarConductorXLicenciaDiferenteCodigo(String licencia, String codigo);
	public List<Conductor>listarConductorXDocumentoDiferenteCodigo(String documento, String codigo);
	public List<Conductor>buscarConductoresRegistradosEquipoBus(String codigo);

}

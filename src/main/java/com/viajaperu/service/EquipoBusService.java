package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import com.viajaperu.models.Conductor;
import com.viajaperu.models.EquipoBus;
import com.viajaperu.models.Terramoza;

public interface EquipoBusService {
	
	public List<EquipoBus> listaEquipoBus();
	public EquipoBus registraActualizaEquipo(EquipoBus equipo );
	public void eliminarEquipo(String codigo);
	public String ultimoIdEquipo();
	public Optional<EquipoBus> buscarPorId(String codigo);

	public List<EquipoBus> listarEquipoTerramoza(String codTerramoza);
	public List<EquipoBus> buscarconductorrepetido(String chofer,String copiloto);
	
	public List<Conductor>conductoresDisponibles();
	
	public List<Conductor>copilotosDisponibles(String cod_conductor);
	
	public List<Terramoza>terramozasDisponibles();
}

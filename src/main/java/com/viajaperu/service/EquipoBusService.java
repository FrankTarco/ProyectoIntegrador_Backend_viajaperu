package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import com.viajaperu.models.EquipoBus;

public interface EquipoBusService {
	
	public List<EquipoBus> listaEquipoBus();
	public EquipoBus registraActualizaEquipo(EquipoBus equipo );
	public void eliminarEquipo(String codigo);
	public String ultimoIdEquipo();
	public Optional<EquipoBus> buscarPorId(String codigo);

	public List<EquipoBus> listarEquipoTerramoza(String codTerramoza);
	public List<EquipoBus> buscarconductorrepetido(String chofer,String copiloto);
}

package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.models.EquipoBus;
import com.viajaperu.repository.EquipoBusRepository;

@Service
public class EquipoBusServiceImp implements EquipoBusService {

	@Autowired
	private EquipoBusRepository repoEquipo;
	
	
	
	@Override
	public List<EquipoBus> listaEquipoBus() {
		// TODO Auto-generated method stub
		return repoEquipo.findAll();
	}

	@Override
	public EquipoBus registraActualizaEquipo(EquipoBus equipo) {
		// TODO Auto-generated method stub
		return repoEquipo.save(equipo);
	}

	@Override
	public void eliminarEquipo(String codigo) {
		// TODO Auto-generated method stub
		repoEquipo.deleteById(codigo);
		
	}

	@Override
	public String ultimoIdEquipo() {
		// TODO Auto-generated method stub
		return repoEquipo.ultimoId();
	}

	@Override
	public Optional<EquipoBus> buscarPorId(String codigo) {
		// TODO Auto-generated method stub
		return repoEquipo.findById(codigo);
	}



	@Override
	public List<EquipoBus> listarEquipoTerramoza(String codTerramoza) {
		// TODO Auto-generated method stub
		return repoEquipo.findByCodterramoza(codTerramoza);
	}

	@Override
	public List<EquipoBus> buscarconductorrepetido(String chofer, String copiloto) {
		// TODO Auto-generated method stub
		return repoEquipo.buscarconductordentrodeEquipo(chofer, copiloto);
	}

}

package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.models.Conductor;
import com.viajaperu.models.EquipoBus;
import com.viajaperu.models.Terramoza;
import com.viajaperu.repository.ConductorRepository;
import com.viajaperu.repository.EquipoBusRepository;
import com.viajaperu.repository.TerramozaRepository;

@Service
public class EquipoBusServiceImp implements EquipoBusService {

	@Autowired
	private EquipoBusRepository repoEquipo;
	
	@Autowired
	private ConductorRepository conductorRepo;
	
	@Autowired
	private TerramozaRepository terraRepo;
	
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

	@Override
	public List<Conductor> conductoresDisponibles() {
		
		return conductorRepo.disponiblesSeleccion();
	}

	@Override
	public List<Conductor> copilotosDisponibles(String cod_conductor) {
		
		return conductorRepo.buscarCopilotoParaEquipoBus(cod_conductor);
	}

	@Override
	public List<Terramoza> terramozasDisponibles() {
		
		return terraRepo.terramozaDisponibleParaEquipo();
	}
	


}

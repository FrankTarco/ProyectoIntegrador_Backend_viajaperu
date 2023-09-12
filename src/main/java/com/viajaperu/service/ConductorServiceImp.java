package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.models.Conductor;
import com.viajaperu.repository.ConductorRepository;
@Service
public class ConductorServiceImp implements ConductorService{

	
	@Autowired
	private ConductorRepository repoconductor;
	
	
	@Override
	public List<Conductor> listarConductor() {
		// TODO Auto-generated method stub
		return repoconductor.findAll();
	}

	@Override
	public Conductor registrarActualizarConductor(Conductor conductor) {
		// TODO Auto-generated method stub
		return repoconductor.save(conductor);
	}

	@Override
	public void eliminarConductor(String codigo) {
		// TODO Auto-generated method stub
		repoconductor.deleteById(codigo);
	}

	@Override
	public String ultimoIdString() {
		// TODO Auto-generated method stub
		return repoconductor.ultimoId();
	}

	@Override
	public Optional<Conductor> buscarPorId(String codigo) {
		// TODO Auto-generated method stub
		return repoconductor.findById(codigo);
	}

	@Override
	public List<Conductor> listarConductorXLicencia(String licencia) {
		// TODO Auto-generated method stub
		return repoconductor.findByNrolicencia(licencia);
	}

	@Override
	public List<Conductor> listarConductorXLicenciaDiferenteCodigo(String licencia, String codigo) {
		// TODO Auto-generated method stub
		return repoconductor.buscarPorLicenciaDiferenteCodigo(licencia, codigo);
	}

}

package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.repository.TerramozaRepository;
import com.viajaperu.models.Terramoza;

@Service
public class TerramozaServiceImp implements TerramozaService{

	@Autowired
	private TerramozaRepository repository;
	
	@Override
	public List<Terramoza> listaTerramoza() {
		return repository.findAll();
	}

	@Override
	public Terramoza insertaTerramoza(Terramoza objTerramoza) {
		return repository.save(objTerramoza);
	}

	@Override
	public Terramoza actualizaTerramoza(Terramoza objTerramoza) {
		return repository.save(objTerramoza);
	}

	@Override
	public void eliminaTerramoza(String codigo) {
		repository.deleteById(codigo);
	}

	@Override
	public Optional<Terramoza> buscaTerramoza(String cod_terramoza) {
		return repository.findById(cod_terramoza);
	}

	@Override
	public String ultimoRegistroString() {
		return repository.ultimoId();
	}

	@Override
	public List<Terramoza> buscarTerramozaPorDni(String numero_documento) {
		return repository.findByNumerodocumento(numero_documento);
	}

	@Override
	public List<Terramoza> buscarTerramozaPorDniyCodigo(String numero_documento, String cod_terramoza) {
		return repository.buscarTerramoza(numero_documento, cod_terramoza);
	}


}

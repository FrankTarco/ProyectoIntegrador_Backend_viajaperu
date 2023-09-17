package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.models.Pasajero;
import com.viajaperu.repository.PasajeroRepository;

@Service
public class PasajeroServiceImp implements PasajeroService{

	
	@Autowired
	private PasajeroRepository repo;
	
	@Override
	public List<Pasajero> listarPasajeros() {
		
		return repo.findAll();
	}

	@Override
	public Pasajero registrarActualizar(Pasajero pasajero) {
		
		return repo.save(pasajero);
	}

	@Override
	public Optional<Pasajero> buscarPasajeroXCodigo(String codigo) {
		
		return repo.findById(codigo);
	}

	@Override
	public List<Pasajero> buscarPasajeroXDocumento(String numeroDocumento) {
		
		return repo.findByNumeroDocumento(numeroDocumento);
	}

	@Override
	public String codigoPasajeroString() {
		
		return repo.ultimoCodigoPasajero();
	}

}

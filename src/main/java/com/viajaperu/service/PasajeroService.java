package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import com.viajaperu.models.Pasajero;

public interface PasajeroService {

	public List<Pasajero>listarPasajeros();
	public Pasajero registrarActualizar(Pasajero pasajero);
	public Optional<Pasajero>buscarPasajeroXCodigo(String codigo);
	public List<Pasajero>buscarPasajeroXDocumento(String numeroDocumento);
	public String codigoPasajeroString();
	
}

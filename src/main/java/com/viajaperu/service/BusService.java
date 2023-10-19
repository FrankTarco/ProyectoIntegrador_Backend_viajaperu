package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import com.viajaperu.models.Bus;

public interface BusService {

	public List<Bus> listarBuses();
	public Bus registrarActualizarBus(Bus bus);
	public void eliminarBus(String codigo);
	public String ultimoIdString();
	public Optional<Bus> buscarPorId(String codigo);
	public List<Bus>listarBusXPlaca(String placa);
	public List<Bus>listarBusXPlacaDiferenteCodigo(String placa, String codigo);
	public List<Bus>encontrarBusenItinerario(String codigo);
	
}

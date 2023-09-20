package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import com.viajaperu.models.Itinerario;


public interface ItinerarioService {
	public List<Itinerario> listaItinerario();
	public Itinerario registraActualizaItinerario(Itinerario itinerario);
	public void eliminarItinerario(String cod_itinerario);
	public String ultimoIdItinerario();
	public Optional<Itinerario> buscarPorId(String codigo);
	public List<Itinerario> listarEquipo(String codequipo);
	public List<Itinerario> listarBus(String codbus);
	public List<Itinerario> buscarDestino(String codorigen,String codllegada);
}

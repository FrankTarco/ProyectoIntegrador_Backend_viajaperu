package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.models.Itinerario;
import com.viajaperu.repository.ItinerarioRepository;


@Service
public class ItinerarioServiceImp implements ItinerarioService{
	
	@Autowired
	private ItinerarioRepository repository;

	@Override
	public List<Itinerario> listaItinerario() {
		return repository.findAll();
	}

	@Override
	public Itinerario registraActualizaItinerario(Itinerario itinerario) {
		return repository.save(itinerario);
	}

	@Override
	public void eliminarItinerario(String cod_itinerario) {
		repository.deleteById(cod_itinerario);
	}

	@Override
	public String ultimoIdItinerario() {
		return repository.ultimoId();
	}

	@Override
	public Optional<Itinerario> buscarPorId(String codigo) {
		return repository.findById(codigo);
	}

	@Override
	public List<Itinerario> listarEquipo(String codequipo) {
		return repository.findByCodequipo(codequipo);
	}
	
	@Override
	public List<Itinerario> listarBus(String codbus) {
		return repository.findByCodbus(codbus);
	}
	
	@Override
	public List<Itinerario> buscarDestino(String codorigen, String codllegada) {
		return repository.buscarDestino(codorigen, codllegada);
	}

	@Override
	public List<Itinerario> itinerariosDisponiblesCliente(String origen, String llegada, String fecha) {
		
		return repository.listarItinerariosPorNombresyFecha(origen, llegada, fecha);
	}

	@Override
	public List<Itinerario> listarItinerariosPorBus(String codbus) {
		// TODO Auto-generated method stub
		return repository.buscarItinerariosPorCodigoBus(codbus);
	}

	@Override
	public List<Itinerario> listarItinerariosPorEquipo(String codequipo) {
		// TODO Auto-generated method stub
		return repository.buscarItinerarioPorCodigoEquipo(codequipo);
	}

	
	
	

}

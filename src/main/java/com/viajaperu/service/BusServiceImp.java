package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.models.Bus;
import com.viajaperu.repository.BusRepository;

@Service
public class BusServiceImp implements BusService{

	@Autowired
	private BusRepository repo;
	
	@Override
	public List<Bus> listarBuses() {
		
		return repo.findAll();
	}

	@Override
	public Bus registrarActualizarBus(Bus bus) {
		
		return repo.save(bus);
	}

	@Override
	public void eliminarBus(String codigo) {
		
		repo.deleteById(codigo);
		
	}

	@Override
	public String ultimoIdString() {
		
		return repo.ultimoId();
	}

	@Override
	public Optional<Bus>buscarPorId(String codigo) {
		
		return repo.findById(codigo);
	}

	@Override
	public List<Bus> listarBusXPlaca(String placa) {
		
		return repo.findByPlaca(placa);
	}

	@Override
	public List<Bus> listarBusXPlacaDiferenteCodigo(String placa, String codigo) {
		
		return repo.buscarPorPlacaDiferenteCodigo(placa, codigo);
	}

}

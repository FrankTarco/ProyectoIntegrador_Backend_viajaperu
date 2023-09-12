package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.models.Destino;
import com.viajaperu.repository.DestinoRepository;

@Service
public class DestinoServiceImp implements DestinoService{

	@Autowired
	private DestinoRepository repo;
	
	@Override
	public Destino registarActualizar(Destino destino) {
		
		return repo.save(destino);
	}

	@Override
	public void eliminarDestino(String codigo) {
		
		repo.deleteById(codigo);		
	}

	@Override
	public List<Destino> listarTodo() {
		
		return repo.findAll();
	}

	@Override
	public Optional<Destino> buscarDestinoPorCodigo(String codigo) {
		
		return repo.findById(codigo);
	}

	@Override
	public String ultimoRegistroString() {
		
		return repo.ultimoId();
	}

	@Override
	public List<Destino> buscarDestinoDiferenteMismo(String nombre,String sucursal,String codigo) {
		
		return repo.buscarDestinoDiferenteDelMismo(nombre,sucursal,codigo);
	}

	@Override
	public List<Destino> buscarPorNombreDestino(String nombre) {
		
		return repo.findByNombre(nombre);
	}

	@Override
	public List<Destino> buscarPorNombreAndSucursal(String nombre, String sucursal) {
		
		return repo.findByNombreAndSucursal(nombre, sucursal);
	}

	
}

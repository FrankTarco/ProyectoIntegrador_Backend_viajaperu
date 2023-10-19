package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import com.viajaperu.models.Destino;

public interface DestinoService {

	public Destino registarActualizar(Destino destino);
	public void eliminarDestino(String codigo);
	public List<Destino>listarTodo();
	public Optional<Destino> buscarDestinoPorCodigo(String codigo);
	public String ultimoRegistroString();
	public List<Destino> buscarDestinoDiferenteMismo(String nombre,String sucursal,String codigo);
	public List<Destino>buscarPorNombreDestino(String nombre);
	public List<Destino>buscarPorNombreAndSucursal(String nombre, String sucursal);
	public List<Destino>destinoDiferenteDeOtro(String nombreDestino);
	
	public List<String>listarDestinosDiferentes();
	public List<String>listarDestinosDiferentesSiMismos(String origen);
	public List<Destino>validarEliminarDestino(String codigo);
}

package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import com.viajaperu.models.Cliente;

public interface ClienteService {

	public List<Cliente>listar();
	public Cliente registrar(Cliente cliente);
	public Optional<Cliente>buscarXCodigo(String codigo);
	public String ultimoCodigo();
	public List<Cliente>buscarPorDocumento(String numero_documento);
	
	public Cliente clientPorDocument(String numero);
	
}

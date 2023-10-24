package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viajaperu.models.Cliente;
import com.viajaperu.repository.ClienteRepository;
import com.viajaperu.utils.Utilidades;


@Service
public class ClienteServiceImp implements ClienteService{

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public List<Cliente> listar() {
		
		return repo.findAll();
	}

	@Override
	public Cliente registrar(Cliente cliente) {
		
		Utilidades util = new Utilidades();
		String codigo = util.generarIdTrasaccion(repo.ultimoCodigoString(),"CLIENTE");
		
		cliente.setCod_cliente(codigo);
		
		return repo.save(cliente);
	}

	@Override
	public Optional<Cliente> buscarXCodigo(String codigo) {
		
		return repo.findById(codigo);
	}

	@Override
	public String ultimoCodigo() {
		
		return repo.ultimoCodigoString();
	}

	@Override
	public List<Cliente> buscarPorDocumento(String numero_documento) {
		
		return repo.findByNumeroDocumento(numero_documento);
	}

	@Override
	public Cliente clientPorDocument(String numero) {
		// TODO Auto-generated method stub
		return repo.encontrarPorDocumento(numero);
	}

}

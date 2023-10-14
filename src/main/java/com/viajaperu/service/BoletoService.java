package com.viajaperu.service;

import java.util.List;

import com.viajaperu.models.Boleto;

public interface BoletoService {

	public Boleto registrar(Boleto objBoleto);
	
	public List<Integer>listarBoletosVendidos(String codigo);
	
}

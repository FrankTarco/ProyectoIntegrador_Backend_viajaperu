package com.viajaperu.service;

import java.util.List;
import java.util.Optional;

import com.viajaperu.models.VentaBoleto;

public interface VentaBoletoService {

	public VentaBoleto registarVentaBoleto(VentaBoleto objVentaBoleto);
	
	public List<VentaBoleto>listarVentas();
	
	public VentaBoleto buscarBoletaPorCodigo(String codigo);
	
	public Optional<VentaBoleto>encontrarVenta(String codigo);
}

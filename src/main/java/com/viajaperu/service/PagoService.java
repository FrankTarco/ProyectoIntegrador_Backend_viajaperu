package com.viajaperu.service;


import java.util.Optional;

import com.viajaperu.models.Pago;

public interface PagoService {

	public Pago registrarPago(Pago objPago);
	
	public Pago buscarPagoPorNumero(String numero);
	
	public Optional<Pago>buscarPagoPorCodigo(String codigo);
	
	public Pago ultimoPago();
	
}

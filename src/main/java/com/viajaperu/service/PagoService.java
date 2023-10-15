package com.viajaperu.service;

import com.viajaperu.models.Pago;

public interface PagoService {

	public Pago registrarPago(Pago objPago);
	
	public Pago buscarPagoPorNumero(String numero);
}

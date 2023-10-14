package com.viajaperu.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaRequest {

	private VentaBoleto ventaBoleto;
	private List<Pasajero>pasajeros;
	private List<Boleto>boletos;
	private Cliente cliente;
	private Pago pago;
	
}

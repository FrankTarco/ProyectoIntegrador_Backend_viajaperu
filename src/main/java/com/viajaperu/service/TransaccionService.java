package com.viajaperu.service;

import java.util.List;

import com.viajaperu.models.Boleto;
import com.viajaperu.models.Cliente;
import com.viajaperu.models.Pago;
import com.viajaperu.models.Pasajero;
import com.viajaperu.models.VentaBoleto;

public interface TransaccionService {

	public Pago registrarPago(Pago objPago, VentaBoleto objVenta, List<Pasajero>objPasajeros,Cliente objCliente);
	
	public Pago registrarPago2(VentaBoleto objVenta,List<Pasajero>lstPasajeros,List<Boleto>lstBoletos,Cliente objCliente,Pago objPago);
	
}

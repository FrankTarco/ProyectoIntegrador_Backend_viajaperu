package com.viajaperu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viajaperu.models.Boleto;
import com.viajaperu.models.Cliente;
import com.viajaperu.models.Pago;
import com.viajaperu.models.Pasajero;
import com.viajaperu.models.VentaBoleto;
import com.viajaperu.repository.BoletoRepository;
import com.viajaperu.repository.PagoRepository;
import com.viajaperu.repository.VentaBoletoRepository;
import com.viajaperu.utils.Utilidades;

@Service
@Transactional
public class TransaccionServiceImp implements TransaccionService{

	@Autowired
	private VentaBoletoRepository ventaBolRepo;
	
	@Autowired
	private BoletoRepository boletoRepo;
	
	@Autowired
	private PasajeroService pasajeroRepo;
	
	@Autowired
	private ClienteService clienteRepo;
	
	@Autowired
	private PagoRepository pagoRepo;
	
	@Override
	public Pago registrarPago(Pago objPago, VentaBoleto objVenta, List<Pasajero>objPasajeros, Cliente objCliente) {
		
		//VARIABLES
		Utilidades util = new Utilidades();
		String codigoPasajero;
		String codigoBoleto;
		String codigoVentaBoleto;
		String codigoCliente;
		String codigoPago;
		int contadorBoleto = 0;
		
		
		//FOR ANIDADO PARA REGISTRAR PASAJEROS Y BOLETOS
		for(Pasajero pasa : objPasajeros) {
			codigoPasajero = util.generarId(pasajeroRepo.codigoPasajeroString(), "Pasajero");
			pasa.setCod_pasajero(codigoPasajero);
			pasajeroRepo.registrarActualizar(pasa);
			
			for (int i = contadorBoleto; i < 10;) {
				Boleto been =  new Boleto(); // objVenta.getBoletos().get(i);
				been.setVenta(objVenta);
				been.setPasajero(pasa);
				codigoBoleto = util.generarIdTrasaccion(boletoRepo.ultimoCodigoBoleto(),"boleto");
				been.setCod_boleto(codigoBoleto);
				boletoRepo.save(been);
				contadorBoleto++;
				break;
			}
			
		}
		
		//REGISTRO DE LA VENTA DEL BOLETO
		codigoVentaBoleto = util.generarIdTrasaccion(ventaBolRepo.ultimoCodVenta(), "Venta");
		objVenta.setCod_venta(codigoVentaBoleto);
		ventaBolRepo.save(objVenta);
		
		//REGISTRO DEL CLIENTE
		codigoCliente = util.generarIdTrasaccion(clienteRepo.ultimoCodigo(), "Cliente");
		objCliente.setCod_cliente(codigoCliente);
		clienteRepo.registrar(objCliente);
		
		//REGISTRO DEL PAGO
		codigoPago = util.generarIdTrasaccion(pagoRepo.ultimoCodigoPago(), "Pago");
		objPago.setCod_pago(codigoPago);
		objPago.setCliente(objCliente);
		
		return pagoRepo.save(objPago);
	}

	@Override
	public Pago registrarPago2(VentaBoleto objVenta, List<Pasajero> lstPasajeros, List<Boleto> lstBoletos,
			Cliente objCliente, Pago objPago) {
		
		Utilidades util = new Utilidades();
		String codigoPasajero;
		String codigoBoleto;
		String codigoVentaBoleto;
		String codigoCliente;
		String codigoPago;
		int contadorBoleto = 0;
		
		//REGISTRAR PRIMERO LA VENTA DEL BOLETO
		codigoVentaBoleto = util.generarIdTrasaccion(ventaBolRepo.ultimoCodVenta(), "Venta");
		objVenta.setCod_itinerario(codigoVentaBoleto);
		VentaBoleto vb = ventaBolRepo.save(objVenta);
		
		//REGISTRAR LOS PASAJEROS Y LOS BOLETOS EN SIMULTANEO
		
		for(Pasajero pasa : lstPasajeros) {
			codigoPasajero = util.generarId(pasajeroRepo.codigoPasajeroString(), "Pasajero");
			pasa.setCod_pasajero(codigoPasajero);
			pasajeroRepo.registrarActualizar(pasa);
			
			for (int i = contadorBoleto; i < lstBoletos.size();) {
				Boleto been =  lstBoletos.get(i);
				been.setCod_venta(vb.getCod_venta());
				been.setCod_pasajero(codigoPasajero);
				codigoBoleto = util.generarIdTrasaccion(boletoRepo.ultimoCodigoBoleto(),"Boleto");
				been.setCod_boleto(codigoBoleto);
				boletoRepo.save(been);
				contadorBoleto++;
				break;
			}
			
		}
		
		//REGISTRO DEL CLIENTE
				codigoCliente = util.generarIdTrasaccion(clienteRepo.ultimoCodigo(), "Cliente");
				objCliente.setCod_cliente(codigoCliente);
				Cliente reg = clienteRepo.registrar(objCliente);
				
				//REGISTRO DEL PAGO
				codigoPago = util.generarIdTrasaccion(pagoRepo.ultimoCodigoPago(), "Pago");
				objPago.setCod_pago(codigoPago);
				objPago.setCod_cliente(reg.getCod_cliente());
				objPago.setCod_venta(vb.getCod_venta());
		
		
		return pagoRepo.save(objPago);
	}

}

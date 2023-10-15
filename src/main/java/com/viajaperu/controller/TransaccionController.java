package com.viajaperu.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viajaperu.creditcard.TarjetaCredito;
import com.viajaperu.creditcard.TarjetaCreditoService;
import com.viajaperu.models.Pago;
import com.viajaperu.models.VentaBoleto;
import com.viajaperu.models.VentaRequest;
import com.viajaperu.service.PagoService;
import com.viajaperu.service.TransaccionService;
import com.viajaperu.service.VentaBoletoService;
import com.viajaperu.utils.AppSettings;

@RestController()
@RequestMapping("/rest/transaccion")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class TransaccionController {

	@Autowired
	private TransaccionService service;
	
	@Autowired
	private VentaBoletoService vbService;
	
	@Autowired
	private TarjetaCreditoService tcService;
	
	@Autowired PagoService pservice;
	
	@PostMapping()
	public ResponseEntity<?>registrar(@RequestBody VentaRequest request){
		
		HashMap<String, Object> salida = new HashMap<>();
		
		try {
			List<TarjetaCredito>tarjeta_existe = tcService.verificarTarjetaPorNumero(request.getPago().getNumero_tarjeta());
			if(CollectionUtils.isEmpty(tarjeta_existe)) {
				salida.put("mensaje", "Error: El numero de tarjeta proporcionado no existe");
			}
			else {
				
				if(tarjeta_existe.get(0).getCredito()<request.getVentaBoleto().getPrecioTotal()) {
					salida.put("mensaje", "Error: El saldo en la tarjeta es insuficiente");						
				}
				else {
					Pago pago_registro = service.registrarPago2(request.getVentaBoleto(),request.getPasajeros(),request.getBoletos(),request.getCliente(),request.getPago());
						
					if(pago_registro == null) {
						salida.put("mensaje", "Error: No se pudo concetrar su pago");
					}
					else {
						salida.put("mensaje", "Se registro el pago correctamente");
						tarjeta_existe.get(0).setCredito(tarjeta_existe.get(0).getCredito()-request.getVentaBoleto().getPrecioTotal());
						tcService.actualizarRegistrar(tarjeta_existe.get(0));
					}			
				}						
			}
				
		} catch (Exception e) {
			salida.put("mensaje", "No se pudo conectar a la BD");
		}
		
		return ResponseEntity.ok(salida);
	}
	
	
	@PostMapping("/ventaBoleto")
	public ResponseEntity<?>registraVentaBoleto(@RequestBody VentaBoleto objVenta){
		
		vbService.registarVentaBoleto(objVenta);
		
		return ResponseEntity.ok("Se realizo la operacion");
	}
	
	@GetMapping("/vertarjeta/{numero}")
	public ResponseEntity<?>verificarTarjeta(@PathVariable("numero")String numero){
		
		return ResponseEntity.ok(tcService.verificarTarjetaPorNumero(numero));
	}
	
	@PutMapping("/actualizatarjeta")
	public ResponseEntity<?>actualizarTarjeta(@RequestBody TarjetaCredito obj){
		HashMap<String, String>salida = new HashMap<>();
		
		try {
			List<TarjetaCredito>ubicar_tarjeta=tcService.verificarTarjetaPorNumero(obj.getNumero());
			
			if(CollectionUtils.isEmpty(ubicar_tarjeta)) {
				salida.put("mensaje", "Error: La tarjeta de credito no existe");
			}
			else {
				
				Pago encontrarPago = pservice.buscarPagoPorNumero(obj.getNumero());				
				//ACTUALIZAR EL CREDITO DE LA TARJETA
				obj.setCredito(obj.getCredito()-encontrarPago.getVenta().getPrecioTotal());
				tcService.actualizarRegistrar(obj);
				salida.put("mensaje", "El saldo de la tarjeta fue actualizado");
				
			}
			
		} catch (Exception e) {
			salida.put("mensaje", "Error: No se pudo conectar al sistema");
		}
		
		return ResponseEntity.ok(salida);
	}
	
}

package com.viajaperu.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.viajaperu.models.Pago;
import com.viajaperu.models.VentaBoleto;
import com.viajaperu.models.VentaRequest;
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
	
	@PostMapping()
	public ResponseEntity<?>registrar(@RequestBody VentaRequest request){
		
		HashMap<String, Object> salida = new HashMap<>();
		
		try {
			Pago pago_registro = service.registrarPago2(request.getVentaBoleto(),request.getPasajeros(),request.getBoletos(),request.getCliente(),request.getPago());
			if(pago_registro == null) {
				salida.put("mensaje", "No se pudo concetrar su pago");
			}
			else {
				salida.put("mensaje", "Se registro el pago correctamente");
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
	
}

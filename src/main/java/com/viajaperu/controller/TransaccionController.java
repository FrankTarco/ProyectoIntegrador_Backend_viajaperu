package com.viajaperu.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viajaperu.models.Boleto;
import com.viajaperu.models.Cliente;
import com.viajaperu.models.Pago;
import com.viajaperu.models.Pasajero;
import com.viajaperu.models.VentaBoleto;
import com.viajaperu.service.TransaccionService;
import com.viajaperu.utils.AppSettings;

@RestController()
@RequestMapping("/rest/transaccion")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class TransaccionController {

	@Autowired
	private TransaccionService service;
	
	
	@PostMapping()
	public ResponseEntity<?>registrar(@RequestBody VentaBoleto venta,@RequestBody List<Pasajero> lstPasajeros,List<Boleto> lstBoletos,
										@RequestBody Cliente cliente,@RequestBody Pago pago){
		
		HashMap<String, Object> salida = new HashMap<>();
		
		try {
			Pago pago_registro = service.registrarPago2(venta,lstPasajeros,lstBoletos,cliente, pago);
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
	
	
	@PostMapping("/registro")
	public ResponseEntity<?>registro(@RequestBody VentaBoleto venta,@RequestBody List<Pasajero> lstPasajeros,
										@RequestBody Cliente cliente,@RequestBody Pago pago){
		
		HashMap<String, Object> salida = new HashMap<>();
		
		try {
			Pago pago_registro = service.registrarPago(pago, venta, lstPasajeros, cliente);
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
	};
	
}
